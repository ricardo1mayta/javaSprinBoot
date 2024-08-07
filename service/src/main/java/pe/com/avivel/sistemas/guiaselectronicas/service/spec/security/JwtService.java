package pe.com.avivel.sistemas.guiaselectronicas.service.spec.security;

import io.jsonwebtoken.JwtException;
import org.springframework.security.core.GrantedAuthority;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.command.security.LoginDTO;
import pe.com.avivel.sistemas.guiaselectronicas.entities.session.DatosSesion;

import java.util.List;

public interface JwtService {

    String login(LoginDTO loginDTO);

    boolean isBearer(String authorization);

    DatosSesion validarSession(String authorization) throws JwtException;

    void logout(String authorization);

    Integer getUsuarioId(String authorization) throws JwtException;

    String getUsername(String authorization) throws JwtException;

    List<GrantedAuthority> getAuthorities(String authorization);
}
