package pe.com.avivel.sistemas.guiaselectronicas.entities.session;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DatosSesion {

    Integer usuarioId;

    String username;

    String usuarioApellidos;

    String usuarioNombres;

    String usuarioEmail;

    boolean usuarioExterno;

    public String getNombreCompleto(){
        return Objects.requireNonNullElse(this.usuarioApellidos, "") + ", " +
                Objects.requireNonNullElse(this.usuarioNombres, "");
    }
}
