package pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.Objects;


@Data
@NoArgsConstructor
public class UsuarioQueryDTO {

    private Integer id;

    private String username;

    @JsonIgnore
    private String password;

    private String apellidos;

    private String nombres;

    private String email;

    private boolean usuarioExterno;

    private Date ultimoLogin;

    private List<RolQueryDTO> roles;

    private boolean activo;

    public String getNombreCompleto(){
        return Objects.requireNonNullElse(this.apellidos, "") + ", " +
                Objects.requireNonNullElse(this.nombres, "");
    }
}
