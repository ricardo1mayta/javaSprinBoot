package pe.com.avivel.sistemas.guiaselectronicas.dto.models.command.security;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@NoArgsConstructor
public class UsuarioCreateDTO implements Serializable {

    private static final long serialVersionUID = -2149191583467596896L;

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String apellidos;

    @NotBlank
    private String nombres;

    @NotNull
    private boolean usuarioExterno;
}
