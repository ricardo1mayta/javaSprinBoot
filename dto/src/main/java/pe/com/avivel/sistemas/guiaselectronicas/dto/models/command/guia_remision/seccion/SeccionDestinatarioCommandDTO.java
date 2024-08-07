package pe.com.avivel.sistemas.guiaselectronicas.dto.models.command.guia_remision.seccion;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.command.util.EntidadSimpleCommandDTO;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class SeccionDestinatarioCommandDTO {

    @Valid
    @NotNull
    @Schema(description = "Tipo Documento Identidad", defaultValue = "{\"id\": 1 }",
            implementation = Object.class, requiredProperties = {"id"})
    private EntidadSimpleCommandDTO tipoDocumentoIdentidad;

    @Size(max = 15)
    @NotBlank
    @Schema(description = "Nro Documento Identidad", defaultValue = "20562727711")
    private String nroDocumentoIdentidad;

    @Size(max = 250)
    @NotBlank
    @Schema(description = "Razon Social", defaultValue = "Aliovo")
    private String razonSocial;

    @Size(max = 250)
    @NotNull
    @Schema(description = "Direccion", defaultValue = "Av. los Nogales Mza. F Lote. 14 Huertos de Villena")
    private String direccion;

    @Valid
    @Schema(description = "Distrito", defaultValue = "{\"id\": 3207 }", nullable = true,
            implementation = Object.class, requiredProperties = {"id"})
    private EntidadSimpleCommandDTO distrito;
}
