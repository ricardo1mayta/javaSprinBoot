package pe.com.avivel.sistemas.guiaselectronicas.entities.session;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Getter
public class UsuarioDetails extends User implements UserDetails {

    private static final long serialVersionUID = 8617967995945093790L;

    private final DatosSesion datosSesion;

    public UsuarioDetails(DatosSesion datosSesion,
                          String username,
                          String password,
                          boolean enabled,
                          boolean accountNonExpired,
                          boolean credentialsNonExpired,
                          boolean accountNonLocked,
                          Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.datosSesion = datosSesion;
    }

}
