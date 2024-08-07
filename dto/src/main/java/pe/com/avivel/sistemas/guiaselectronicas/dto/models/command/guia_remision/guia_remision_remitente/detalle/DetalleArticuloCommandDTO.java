package pe.com.avivel.sistemas.guiaselectronicas.dto.models.command.guia_remision.guia_remision_remitente.detalle;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.command.util.EntidadSimpleCommandDTO;

import javax.validation.Valid;
import javax.validation.constraints.*;

@Data
@NoArgsConstructor
public class DetalleArticuloCommandDTO {

    @Schema(description = "Id", nullable = true)
    private Integer id;

    @Min(1)
    @NotNull
    @Schema(description = "Nro orden", defaultValue = "1")
    private Integer nroOrden;

    @Min(0)
    @Digits(integer = 12, fraction = 10)
    @NotNull
    @Schema(description = "Cantidad", defaultValue = "5")
    private Double cantidad;

    @Valid
    @NotNull
    @Schema(description = "Unidad de Medida", defaultValue = "{\"id\": 43 }",
            implementation = Object.class, requiredProperties = {"id"})
    private EntidadSimpleCommandDTO unidadMedida;

    @Size(max = 30)
    @NotBlank
    @Schema(description = "Codigo", defaultValue = "HUEPTE010058")
    private String codigo;

    @Size(max = 500)
    @NotBlank
    @Schema(description = "Descripcion", defaultValue = "HUEVO DE GALLINA -YEMITA NARANJA X 12 UND")
    private String descripcion;

    @Digits(integer = 10, fraction = 2)
    @Schema(description = "Peso", defaultValue = "5", nullable = true)
    private Double peso;

    @Size(max = 500)
    @Schema(description = "Observacion", defaultValue = "Observacion", nullable = true)
    private String observacion;
}
