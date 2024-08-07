package pe.com.avivel.sistemas.guiaselectronicas.dto.models.command.guia_remision.guia_remision_remitente.detalle;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

import static pe.com.avivel.sistemas.guiaselectronicas.dto.models.command.guia_remision.guia_remision_remitente.GuiaRemisionRemitenteCommandDTO.*;

@Data
@NoArgsConstructor
public class DetalleVehiculoCommandDTO {

    @Schema(description = "Id", nullable = true)
    private Integer id;

    @Min(1)
    @NotNull
    @Schema(description = "Nro orden", defaultValue = "1")
    private Integer nroOrden;

    @Size(max = 8)
    @NotBlank
    @Pattern(regexp = ALPHANUMERIC_PATTERN, message = ALPHANUMERIC_MESSAGE)
    @Schema(description = "Nro placa", defaultValue = "W2O835")
    private String numeroPlaca;

    @Size(max = 255)
    @Schema(description = "Descripcion", defaultValue = "Avivel", nullable = true)
    private String descripcion;

    @Size(max = 50)
    @Schema(description = "Entidad autorizadora", defaultValue = "MTC", nullable = true)
    private String entidadAutorizadora;

    @Size(max = 50)
    @Schema(description = "Nro autorizacion", defaultValue = "REG00001", nullable = true)
    private String nroAutorizacion;
}
