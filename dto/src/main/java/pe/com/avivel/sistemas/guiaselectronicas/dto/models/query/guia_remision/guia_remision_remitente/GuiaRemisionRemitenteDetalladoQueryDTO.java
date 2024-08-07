package pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.guia_remision.guia_remision_remitente;

import lombok.Data;
import lombok.NoArgsConstructor;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.auxiliar.guia_remision.EstadoGuiaQueryDTO;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.guia_remision.CorreoDestinatarioQueryDTO;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.guia_remision.DocumentoRelacionadoQueryDTO;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.guia_remision.guia_remision_remitente.detalle.ArticuloQueryDTO;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.guia_remision.guia_remision_remitente.detalle.ConductorQueryDTO;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.guia_remision.guia_remision_remitente.detalle.VehiculoQueryDTO;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.guia_remision.seccion.*;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class GuiaRemisionRemitenteDetalladoQueryDTO {

    private Integer id;

    private String serie;

    private String numero;

    private String ordenPedido;

    private String ordenCompra;

    private String usuarioEmision;

    private Date fechaEmision;

    private Date fechaVencimiento;

    private String observacion;

    private Integer nroItems;

    private Date fechaGeneracionXml;

    private List<DocumentoRelacionadoQueryDTO> documentoRelacionados;

    private List<CorreoDestinatarioQueryDTO> correoDestinatarios;

    private DestinatarioQueryDTO destinatario;

    private EnvioQueryDTO envio;

    private List<ConductorQueryDTO> conductores;

    private List<VehiculoQueryDTO> vehiculos;

    private TransportistaQueryDTO transportista;

    private EntidadQueryDTO proveedor;

    private EntidadQueryDTO comprador;

    private DireccionQueryDTO puntoPartida;

    private DireccionQueryDTO puntoLlegada;

    private List<ArticuloQueryDTO> articulos;

    private EstadoGuiaQueryDTO estado;
}
