package pe.com.avivel.sistemas.guiaselectronicas.dto.models.command.guia_remision.guia_remision_remitente.detalle;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.command.util.EntidadSimpleCommandDTO;

import javax.validation.Valid;
import javax.validation.constraints.*;

import static pe.com.avivel.sistemas.guiaselectronicas.dto.models.command.guia_remision.guia_remision_remitente.GuiaRemisionRemitenteCommandDTO.*;

@Data
@NoArgsConstructor
public class DetalleConductorCommandDTO {

    @Schema(description = "Id", nullable = true)
    private Integer id;

    @Min(1)
    @NotNull
    @Schema(description = "Nro orden", defaultValue = "1")
    private Integer nroOrden;

    @Valid
    @NotNull
    @Schema(description = "Tipo Documento Identidad", defaultValue = "{\"id\": 2 }",
            implementation = Object.class, requiredProperties = {"id"})
    private EntidadSimpleCommandDTO tipoDocumentoIdentidad;

    @Size(max = 15)
    @NotBlank
    @Schema(description = "Nro Documento Identidad", defaultValue = "65738456")
    private String nroDocumentoIdentidad;

    @Size(max = 250)
    @NotBlank
    @Schema(description = "Apellidos", defaultValue = "Atalaya Casas")
    private String apellidos;

    @Size(max = 250)
    @NotBlank
    @Schema(description = "Nombres", defaultValue = "Bruno")
    private String nombres;

    @Size(max = 10)
    @NotBlank
    @Pattern(regexp = ALPHANUMERIC_PATTERN, message = ALPHANUMERIC_MESSAGE)
    @Schema(description = "Licencia o brevete", defaultValue = "Q75439569")
    private String nroLicenciaBrevete;
}
