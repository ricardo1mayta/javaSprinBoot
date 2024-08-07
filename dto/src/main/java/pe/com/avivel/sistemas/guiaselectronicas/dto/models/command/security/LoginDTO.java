package pe.com.avivel.sistemas.guiaselectronicas.dto.models.command.security;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class LoginDTO {

    @NotNull
    private String username;

    @NotNull
    private String password;

    @Size(max = 255)
    private String cliente = "";

    @Size(max = 255)
    private String ip = "";

    @Size(max = 255)
    private String pais = "";
}
