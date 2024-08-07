package pe.com.avivel.sistemas.guiaselectronicas.dto.models.command.guia_remision.seccion;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.command.guia_remision.groups.motivo_traslado.*;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.command.util.EntidadSimpleCommandDTO;

import javax.validation.Valid;
import javax.validation.constraints.*;

@Data
@NoArgsConstructor
public class SeccionDireccionPartidaCommandDTO {

    @AssertFalse(groups = {GroupCompra.class})
    @AssertTrue(groups = {GroupVenta.class, GroupVentaEntregaTerceros.class, GroupTrasladoEmisorItinerante.class, GroupTrasladoEntreEstablecimientos.class})
    @NotNull
    @Schema(description = "Indicador si el almacen indicado es interno", defaultValue = "true")
    private boolean almacenInterno;

    @Valid
    @Null(groups = {GroupCompra.class})
    @NotNull(groups = {GroupVenta.class, GroupVentaEntregaTerceros.class, GroupTrasladoEmisorItinerante.class, GroupTrasladoEntreEstablecimientos.class})
    @Schema(description = "Almacen", defaultValue = "{\"id\": 1 }", nullable = true,
            implementation = Object.class, requiredProperties = {"id"})
    private EntidadSimpleCommandDTO almacen;

    @Valid
    @Null(groups = {GroupVenta.class, GroupVentaEntregaTerceros.class, GroupTrasladoEmisorItinerante.class, GroupTrasladoEntreEstablecimientos.class})
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
