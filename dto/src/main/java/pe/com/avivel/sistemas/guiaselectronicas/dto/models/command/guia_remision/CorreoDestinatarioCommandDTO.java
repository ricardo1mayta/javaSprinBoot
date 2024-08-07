package pe.com.avivel.sistemas.guiaselectronicas.dto.models.command.guia_remision;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class CorreoDestinatarioCommandDTO {

    @Schema(description = "Id", nullable = true)
    private Integer id;

    @Size(max = 255)
    @Email
    @NotBlank
    @Schema(description = "Correo asociado", defaultValue = "desarrollo.avivel@avivel.com.pe")
    private String correo;
}
