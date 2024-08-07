package pe.com.avivel.sistemas.guiaselectronicas.dto.models.command.guia_remision.seccion;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.command.guia_remision.groups.motivo_traslado.GroupCompra;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.command.guia_remision.groups.motivo_traslado.GroupTrasladoEntreEstablecimientos;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.command.guia_remision.groups.motivo_traslado.GroupVenta;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.command.guia_remision.groups.motivo_traslado.GroupVentaEntregaTerceros;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.command.util.EntidadSimpleCommandDTO;

import javax.validation.Valid;
import javax.validation.constraints.*;

@Data
@NoArgsConstructor
public class SeccionDireccionLlegadaCommandDTO {

    @AssertTrue(groups = {GroupCompra.class, GroupTrasladoEntreEstablecimientos.class})
    @AssertFalse(groups = {GroupVenta.class, GroupVentaEntregaTerceros.class})
    @NotNull
    @Schema(description = "Indicador si el almacen indicado es interno", defaultValue = "false")
    private boolean almacenInterno;

    @Valid
    @NotNull(groups = {GroupCompra.class, GroupTrasladoEntreEstablecimientos.class})
    @Null(groups = {GroupVenta.class, GroupVentaEntregaTerceros.class})
    @Schema(description = "Almacen", defaultValue = "null", nullable = true)
    private EntidadSimpleCommandDTO almacen;

    @Valid
    @Null(groups = {GroupCompra.class, GroupTrasladoEntreEstablecimientos.class})
    @Schema(description = "Entidad Almacen", defaultValue = "null", nullable = true)
    private EntidadSimpleCommandDTO entidadAlmacen;

    @Valid
    @NotNull
    @Schema(description = "Distrito", defaultValue = "{\"id\": 3207 }",
            implementation = Object.class, requiredProperties = {"id"})
    private EntidadSimpleCommandDTO distrito;

    @Size(max = 500)
    @NotBlank
    @Schema(description = "Direccion", defaultValue = "Av. los Nogales Mza. F Lote. 14 Huertos de Villena")
    private String direccion;
}
