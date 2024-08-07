package pe.com.avivel.sistemas.guiaselectronicas.dto.models.command.guia_remision.seccion;

import lombok.Data;
import lombok.NoArgsConstructor;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.command.util.EntidadSimpleCommandDTO;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class SeccionEntidadCommandDTO {

    @Valid
    @NotNull
    private EntidadSimpleCommandDTO tipoDocumentoIdentidad;

    @Size(max = 15)
    @NotBlank
    private String nroDocumentoIdentidad;

    @Size(max = 250)
    @NotBlank
    private String razonSocial;
}
