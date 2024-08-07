package pe.com.avivel.sistemas.guiaselectronicas.entities.session;

import lombok.Getter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
public class CustomUsernamePasswordAuthenticationToken extends UsernamePasswordAuthenticationToken {

    private static final long serialVersionUID = -3454647241150019970L;

    private final DatosSesion datosSesion;

    public CustomUsernamePasswordAuthenticationToken(DatosSesion datosSesion, Collection<? extends GrantedAuthority> authorities) {
        super(datosSesion.getUsername(), null, authorities);
        this.datosSesion = datosSesion;
    }

}
