package pe.com.avivel.sistemas.guiaselectronicas.dto.models.command.guia_remision.seccion;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.command.guia_remision.groups.modalidad_traslado.GroupTrasladoPrivado;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.command.guia_remision.groups.modalidad_traslado.GroupTrasladoPublico;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.command.guia_remision.groups.motivo_traslado.*;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.command.util.EntidadSimpleCommandDTO;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.Date;

@Data
@NoArgsConstructor
public class SeccionEnvioCommandDTO {

    @Valid
    @NotNull
    @Schema(description = "Motivo Traslado", defaultValue = "{\"id\": 10 }",
            implementation = Object.class, requiredProperties = {"id"})
    private EntidadSimpleCommandDTO motivoTraslado;

    @Null(groups = GroupTrasladoPublico.class)
    @NotNull(groups = GroupTrasladoPrivado.class)
    @Schema(description = "Fecha Inicio de Traslado", nullable = true)
    private Date fechaInicioTraslado;

    @Null(groups = GroupTrasladoPrivado.class)
    @NotNull(groups = GroupTrasladoPublico.class)
    @Schema(description = "Fecha de entrega de bienes", defaultValue = "null", nullable = true)
    private Date fechaEntregaBienesTransportista;

    @NotBlank(groups = GroupOtros.class)
    @Size(min = 3, groups = GroupOtros.class)
    @Size(max = 100)
    @Schema(description = "Descripcion motivo traslaldo", defaultValue = "Venta", nullable = true)
    private String descripcionMotivoTraslado;

    @Valid
    @NotNull
    @Schema(description = "Unidad peso bruto", defaultValue = "{\"id\": 1 }",
            implementation = Object.class, requiredProperties = {"id"})
    private EntidadSimpleCommandDTO unidadMedidaPesoBruto;

    @Min(0)
    @Digits(integer = 12, fraction = 3)
    @NotNull
    @Schema(description = "Peso bruto", defaultValue = "100")
    private Double pesoBruto;

    @Valid
    @NotNull
    @Schema(description = "Modalidad de traslado", defaultValue = "{\"id\": 2 }",
            implementation = Object.class, requiredProperties = {"id"})
    private EntidadSimpleCommandDTO modalidadTraslado;

    @NotNull
    @Schema(description = "Indicador si el vehiculo de transporte es de categoria M1 o L", defaultValue = "false")
    private boolean trasladoVehiculosM1L;
}
