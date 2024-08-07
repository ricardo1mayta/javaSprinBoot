package pe.com.avivel.sistemas.guiaselectronicas.dto.models.command.guia_remision.guia_remision_remitente;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import pe.com.avivel.sistemas.guiaselectronicas.dto.annotations.CustomTypeMap;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.command.guia_remision.CorreoDestinatarioCommandDTO;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.command.guia_remision.DocumentoRelacionadoCommandDTO;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.command.guia_remision.groups.categoria_vehiculo.GroupCategoriaDefault;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.command.guia_remision.groups.categoria_vehiculo.GroupCategoriaM1L;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.command.guia_remision.groups.modalidad_traslado.GroupTrasladoPrivadoCategoriaDefault;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.command.guia_remision.groups.modalidad_traslado.GroupTrasladoPublicoCategoriaDefault;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.command.guia_remision.groups.motivo_traslado.*;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.command.guia_remision.guia_remision_remitente.detalle.DetalleArticuloCommandDTO;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.command.guia_remision.guia_remision_remitente.detalle.DetalleConductorCommandDTO;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.command.guia_remision.guia_remision_remitente.detalle.DetalleVehiculoCommandDTO;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.command.guia_remision.seccion.*;
import pe.com.avivel.sistemas.guiaselectronicas.entities.guia_remision.guia_remision_remitente.GuiaRemisionRemitente;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.Date;
import java.util.List;

import static pe.com.avivel.sistemas.guiaselectronicas.dto.models.command.maestros.SerieCommandDTO.*;

@Slf4j
@Schema
@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class GuiaRemisionRemitenteCommandDTO {

    public static final String ALPHANUMERIC_PATTERN = "^[A-Z0-9]+$";
    public static final String ALPHANUMERIC_MESSAGE = "debe ingresar caracteres alfanuméricos en mayusculas.";

    @Size(max = 6)
    @Pattern(regexp = CODIGO_PATTERN, message = CODIGO_MESSAGE )
    @NotBlank
    @Schema(description = "Serie",defaultValue = "T001")
    private String serie;

    @Size(max = 9)
    @NotBlank
    @Schema(description = "Correlativo",defaultValue = "0000001")
    private String numero;

    @Size(max = 50)
    @Schema(description = "Nro de la Orden de Pedido", defaultValue = "OP00001", nullable = true)
    private String ordenPedido;

    @Size(max = 50)
    @Schema(description = "Nro de la Orden de Compra", defaultValue = "OC000001", nullable = true)
    private String ordenCompra;

    @Schema(description = "Fecha de Vencimiento de la guia de remision", nullable = true)
    private Date fechaVencimiento;

    @Schema(description = "Fecha de emision de la guia de remision", nullable = true)
    private Date fechaEmision;

    @Size(max = 250)
    @NotNull
    @Schema(description = "Observacion", defaultValue = "Venta", nullable = true)
    private String observacion;

    @Valid
    @Schema(description = "Listado de documentos asociados", nullable = true)
    private List<DocumentoRelacionadoCommandDTO> documentoRelacionados;

    @Valid
    @Schema(description = "Listado de correos asociados", nullable = true)
    private List<CorreoDestinatarioCommandDTO> correoDestinatarios;

    @Valid
    @NotNull
    @Schema(description = "Datos del destinatario")
    private SeccionDestinatarioCommandDTO destinatario;

    @Valid
    @NotNull
    @Schema(description = "Datos del envio")
    private SeccionEnvioCommandDTO envio;

    @Valid
    @NotEmpty(groups = GroupTrasladoPrivadoCategoriaDefault.class)
    @Null(groups = {GroupCategoriaM1L.class, GroupTrasladoPublicoCategoriaDefault.class})
    @Size(max = 3, groups = GroupCategoriaDefault.class)
    @Schema(description = "Listado de conductores", nullable = true)
    private List<DetalleConductorCommandDTO> conductores;

    @Valid
    @NotEmpty(groups = GroupTrasladoPrivadoCategoriaDefault.class)
    @Null(groups = GroupTrasladoPublicoCategoriaDefault.class)
    @Size(max = 3, groups = GroupCategoriaDefault.class)
    @Size(max = 1, groups = GroupCategoriaM1L.class)
    @Schema(description = "Listado de Vehiculos", nullable = true)
    private List<DetalleVehiculoCommandDTO> vehiculos;

    @Valid
    @Null(groups = GroupTrasladoPrivadoCategoriaDefault.class)
    @NotNull(groups = GroupTrasladoPublicoCategoriaDefault.class)
    @Null(groups = GroupCategoriaM1L.class)
    @Schema(description = "Datos del transportista", nullable = true, defaultValue = "null")
    private SeccionTransportistaCommandDTO transportista;

    @Valid
    @NotNull(groups = {GroupCompra.class})
    @Null(groups = {GroupVenta.class, GroupVentaEntregaTerceros.class, GroupTrasladoEmisorItinerante.class, GroupTrasladoEntreEstablecimientos.class})
    @Schema(description = "Datos del proveedor", nullable = true, defaultValue = "null")
    private SeccionEntidadCommandDTO proveedor;

    @Valid
    @NotNull(groups = {GroupVentaEntregaTerceros.class, GroupOtros.class})
    @Null(groups = {GroupVenta.class, GroupCompra.class, GroupTrasladoEmisorItinerante.class, GroupTrasladoEntreEstablecimientos.class})
    @Schema(description = "Datos del comprador", nullable = true, defaultValue = "null")
    private SeccionEntidadCommandDTO comprador;

    @Valid
    @NotNull
    @Schema(description = "Datos del punto de partida")
    private SeccionDireccionPartidaCommandDTO puntoPartida;

    @Valid
    @Null(groups = GroupTrasladoEmisorItinerante.class)
    @NotNull(groups = {GroupVenta.class, GroupCompra.class, GroupVentaEntregaTerceros.class, GroupOtros.class, GroupTrasladoEntreEstablecimientos.class})
    @Schema(description = "Datos del punto de llegada", defaultValue = "null", nullable = true)
    private SeccionDireccionLlegadaCommandDTO puntoLlegada;

    @Valid
    @NotEmpty
    @Schema(description = "Listado de articulos asociados")
    private List<DetalleArticuloCommandDTO> articulos;

    @CustomTypeMap
    public static void registerTypeMap(ModelMapper modelMapper) {
        TypeMap<GuiaRemisionRemitenteCommandDTO, GuiaRemisionRemitente> propertyMapper =
                modelMapper.createTypeMap(GuiaRemisionRemitenteCommandDTO.class, GuiaRemisionRemitente.class);

        propertyMapper.setPreConverter(context -> {
            GuiaRemisionRemitente destination = context.getDestination();
            if(destination != null){
                destination.setDestinatario(null);
                destination.setEnvio(null);
                destination.setTransportista(null);
                destination.setProveedor(null);
                destination.setPuntoPartida(null);
                destination.setPuntoLlegada(null);
           }
            return destination;
        });

        propertyMapper.addMappings(mapper -> {
            mapper.skip(GuiaRemisionRemitente::setDocumentoRelacionados);
            mapper.skip(GuiaRemisionRemitente::setCorreoDestinatarios);
            mapper.skip(GuiaRemisionRemitente::setConductores);
            mapper.skip(GuiaRemisionRemitente::setVehiculos);
            mapper.skip(GuiaRemisionRemitente::setArticulos);
        });
    }
}
