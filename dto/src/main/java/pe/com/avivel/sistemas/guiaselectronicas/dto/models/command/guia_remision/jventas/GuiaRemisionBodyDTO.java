package pe.com.avivel.sistemas.guiaselectronicas.dto.models.command.guia_remision.jventas;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;


@Data
@NoArgsConstructor
public class GuiaRemisionBodyDTO {

    @Size(max = 5)
    @NotBlank
    private String guiaRemisionSerie;

    @Size(max = 5)
    @NotBlank
    private String guiaRemisionNumero;

    @NotBlank
    private String guiaRemisionTiPoGuia;

    @NotBlank
    private Date fecha;
    private AlmacenDTO puntoPartidaAlmacen;
    private AlmacenDTO puntoLLegadaAlmacen;
    private ClienteDTO cliente;
    private int puntoPartidaIsAlmacenInterno;
    private PuntoPartidaDTO puntoPartida;
    private PuntoDeLlegadaDTO puntoDeLlegada;
    private EmpresaTransportistaDTO empresaTransportista;
    private List<VehiculoDTO> vehiculos;
    private List<ConductorDTO> conductores;
    private MotivoTasladoDTO motivoTraslado;
    private String ordenPedidoNumeroSerie;
    private DocumentoRelacionadoDTO documentoRelacionado;
    private String ordenCompra;
    private String observacion;
    private List<DetalleGuiaRemisionDTO> detalles;
    private List<String> correoDestinatarios;
    private String usuarioEmision;
}
