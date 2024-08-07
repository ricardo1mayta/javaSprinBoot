package pe.com.avivel.sistemas.guiaselectronicas.service.impl.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pe.com.avivel.sistemas.guiaselectronicas.entities.security.Rol;
import pe.com.avivel.sistemas.guiaselectronicas.entities.security.Usuario;
import pe.com.avivel.sistemas.guiaselectronicas.entities.session.DatosSesion;
import pe.com.avivel.sistemas.guiaselectronicas.entities.session.UsuarioDetails;
import pe.com.avivel.sistemas.guiaselectronicas.repository.security.UsuarioRepository;

import java.util.List;
import java.util.stream.Collectors;

import static pe.com.avivel.sistemas.guiaselectronicas.commons.util.Util.ROLE_PREFIX;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsuarioDetailsServiceImpl implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                    "Error en el login: no existe el usuario '" + username + "' en el sistema"));

        List<GrantedAuthority> authorities = usuario
                .getRoles()
                .stream()
                .map(Rol::getNombre)
                .collect(Collectors.toList())
                .stream()
                .map(String::trim)
                .filter(rol -> rol.startsWith("_"))
                .map(role -> new SimpleGrantedAuthority(ROLE_PREFIX + role))
                .peek(authority -> log.info("Role: " + authority.getAuthority()))
                .collect(Collectors.toList());

        DatosSesion datosSesion = DatosSesion
                .builder()
                .username(usuario.getUsername())
                .usuarioId(usuario.getId())
                .usuarioApellidos(usuario.getApellidos())
                .usuarioNombres(usuario.getNombres())
                .usuarioEmail(usuario.getEmail())
                .usuarioExterno(usuario.isUsuarioExterno())
                .build();

        return new UsuarioDetails(datosSesion, usuario.getUsername(), usuario.getPassword(), usuario.isActivo(),
                true, true, true, authorities);
    }
}
