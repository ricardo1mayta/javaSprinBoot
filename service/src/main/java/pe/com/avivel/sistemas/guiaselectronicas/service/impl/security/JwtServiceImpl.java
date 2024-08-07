package pe.com.avivel.sistemas.guiaselectronicas.service.impl.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.command.security.LoginDTO;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.command.security.SesionCommandDTO;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.security.*;
import pe.com.avivel.sistemas.guiaselectronicas.entities.session.DatosSesion;
import pe.com.avivel.sistemas.guiaselectronicas.service.spec.security.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

import static pe.com.avivel.sistemas.guiaselectronicas.service.impl.ConstansResponse.*;
import static pe.com.avivel.sistemas.guiaselectronicas.commons.util.Util.ROLE_PREFIX;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    @Value("${app.token.issuer}")
    private String ISSUER;

    @Value("${app.token.secret-key}")
    private String SECRET_KEY;

    private final UsuarioQueryService usuarioQueryService;

    private final SesionQueryService sesionQueryService;

    private final MenuQueryService menuQueryService;

    private final SesionCommandService sesionCommandService;

    private final BCryptPasswordEncoder passwordEncoder;

    private final ObjectMapper objectMapper;

    private static final String BEARER = "Bearer ";
    private static final String BAD_CREDENTIALS = "Credenciales incorrectas.";
    private static final String ERROR_PROCESAR_JWT = "Error al procesar el JWT.";

    @Override
    public String login(LoginDTO loginDTO) {
        UsuarioQueryDTO usuario = usuarioQueryService.findByUsername(loginDTO.getUsername())
                .orElseThrow(() -> new JwtException(UsuarioResponse.NO_ENCONTRADO));

        if(!usuario.isActivo()){
            throw new JwtException(UsuarioResponse.INACTIVO);
        }

        if(!passwordEncoder.matches(loginDTO.getPassword(), usuario.getPassword())){
            throw new JwtException(BAD_CREDENTIALS);
        }

        SesionCommandDTO sesionCreate = SesionCommandDTO
                .builder()
                .cliente(loginDTO.getCliente())
                .ip(loginDTO.getIp())
                .pais(loginDTO.getPais())
                .usuario(usuario)
                .build();

        SesionQueryDTO sesion = sesionCommandService.create(sesionCreate);

        List<String> roles = usuario.getRoles()
                .stream()
                .sorted(Comparator.comparingInt(RolQueryDTO::getId))
                .map(RolQueryDTO::getNombre)
                .collect(Collectors.toList());

        List<MenuQueryDTO> menus = menuQueryService.listByUsuarioId(usuario.getId());

        InfoAdicionalTokenDTO infoAdicionalToken = InfoAdicionalTokenDTO
                .builder()
                .tokenSession(sesion.getToken())
                .usuarioId(usuario.getId())
                .usuarioNombre(usuario.getNombreCompleto())
                .usuarioEmail(usuario.getEmail())
                .usuarioExterno(usuario.isUsuarioExterno())
                .roles(roles)
                .menus(menus)
                .build();

        Map<String, Object> claims;

        try{
            String jsonInfoAdicionalToken = objectMapper.writeValueAsString(infoAdicionalToken);
            //noinspection unchecked
            claims = objectMapper.readValue(jsonInfoAdicionalToken, Map.class);
        } catch (IOException ex){
            throw new JwtException(ERROR_PROCESAR_JWT);
        }

        String jwt = Jwts
                .builder()
                .setClaims(claims)
                .setSubject(usuario.getUsername())
                .setIssuer(ISSUER)
                .setIssuedAt(sesion.getTokenCreacion())
                .setExpiration(sesion.getTokenExpiracion())
                .signWith(getSigningKey(), SignatureAlgorithm.HS512).compact();

        return BEARER + jwt;
    }

    private Key getSigningKey() {
        byte[] keyBytes = SECRET_KEY.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    @Override
    public boolean isBearer(String authorization) {
        return authorization != null && authorization.startsWith(BEARER) && authorization.split("\\.").length == 3;
    }

    @Override
    public DatosSesion validarSession(String authorization) throws JwtException {
        String tokenSession = this.decodeJWT(authorization)
                .get(InfoAdicionalTokenDTO.TOKEN_SESION).toString();

        SesionQueryDTO sesion = sesionQueryService.findByToken(tokenSession)
                .orElseThrow(() -> new JwtException(SesionResponse.NO_ENCONTRADA));

        if(!sesion.isActivo()){
            throw new JwtException(SesionResponse.TOKEN_EXPIRADO);
        }

        Date fechaActual = new Date();
        if(fechaActual.after(sesion.getTokenExpiracion())){
            desactivarSesion(sesion.getId());
            throw new JwtException(SesionResponse.TOKEN_EXPIRADO);
        }

        Integer usuarioId = this.getUsuarioId(authorization);

        UsuarioQueryDTO usuario = usuarioQueryService.findById(usuarioId)
                .orElseThrow(() -> new JwtException(UsuarioResponse.NO_ENCONTRADO));

        if(!usuario.isActivo()){
            desactivarSesion(sesion.getId());
            throw new JwtException(UsuarioResponse.INACTIVO);
        }

        return DatosSesion
                .builder()
                .username(this.decodeJWT(authorization).getSubject())
                .usuarioId(usuario.getId())
                .usuarioApellidos(usuario.getApellidos())
                .usuarioNombres(usuario.getNombres())
                .usuarioEmail(usuario.getEmail())
                .usuarioExterno(usuario.isUsuarioExterno())
                .build();
    }

    @Override
    public void logout(String authorization) {
        String tokenSession = this.decodeJWT(BEARER + authorization)
                .get(InfoAdicionalTokenDTO.TOKEN_SESION).toString();
        desactivarSesion(tokenSession);
    }

    @Override
    public String getUsername(String authorization) throws JwtException {
        return this.decodeJWT(authorization).getSubject();
    }

    @Override
    public Integer getUsuarioId(String authorization) throws JwtException {
        return this.decodeJWT(authorization).get(InfoAdicionalTokenDTO.USUARIO_ID, Integer.class);
    }

    @Override
    public List<GrantedAuthority> getAuthorities(String authorization) {
        String roles = this.decodeJWT(authorization)
                .get(InfoAdicionalTokenDTO.ROLES).toString()
                .replace("[", "")
                .replace("]", "");

        String[] rolesSep = roles.split(",");

        return Arrays.stream(rolesSep)
                .collect(Collectors.toList())
                .stream()
                .map(String::trim)
                .filter(rol -> rol.startsWith("_"))
                .map(role -> new SimpleGrantedAuthority(ROLE_PREFIX + role))
                .collect(Collectors.toList());
    }

    private void desactivarSesion(String tokenSession){
        SesionQueryDTO sesion = sesionQueryService.findByToken(tokenSession)
                .orElseThrow(() -> new JwtException(SesionResponse.NO_ENCONTRADA));

        if(!sesion.isActivo()){
            return;
        }

        desactivarSesion(sesion.getId());
    }

    private void desactivarSesion(Integer sesionId){
        sesionCommandService.deleteById(sesionId);
    }

    private Claims decodeJWT(String authorization) throws JwtException {
        String token = authorization.split(" ")[1];

        try{
            return Jwts
                    .parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException ex){
            log.info("ExpiredJwtException", ex);
            String tokenSession = ex.getClaims().get(InfoAdicionalTokenDTO.TOKEN_SESION).toString();
            desactivarSesion(tokenSession);
            throw new JwtException(SesionResponse.TOKEN_EXPIRADO);
        } catch (Exception ex){
            throw new JwtException(ERROR_PROCESAR_JWT);
        }
    }
}
