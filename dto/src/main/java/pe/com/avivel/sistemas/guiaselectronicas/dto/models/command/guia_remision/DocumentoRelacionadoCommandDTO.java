package pe.com.avivel.sistemas.guiaselectronicas.dto.models.command.guia_remision;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.command.util.EntidadSimpleCommandDTO;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Schema
@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class DocumentoRelacionadoCommandDTO {

    @Schema(description = "Id", nullable = true)
    private Integer id;

    @Valid
    @NotNull
    @Schema(description = "Tipo Documento", defaultValue = "{\"id\": 2 }",
            implementation = Object.class, requiredProperties = {"id"})
    private EntidadSimpleCommandDTO tipoDocumento;

    @Size(max = 100)
    @NotBlank
    @Schema(description = "Nro Documento", defaultValue = "B001-0043218")
    private String nroDocumento;

    @NotNull
    @Schema(description = "Indicador si el documento fue emitido por la propia empresa o no", defaultValue = "true")
    private boolean emitidoInterno;

    @Size(max = 15)
    @NotNull
    @Schema(description = "RUC Empresa emisor", defaultValue = "20524088810")
    private String rucEmisor;
}
