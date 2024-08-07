package pe.com.avivel.sistemas.guiaselectronicas.dto.models.command.security;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class PasswordUpdateDTO {

    @NotBlank
    private String oldPassword;

    @NotBlank
    private String newPassword;
}
