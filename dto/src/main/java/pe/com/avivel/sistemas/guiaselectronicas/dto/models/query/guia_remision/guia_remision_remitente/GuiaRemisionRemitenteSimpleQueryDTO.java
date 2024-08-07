package pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.guia_remision.guia_remision_remitente;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.auxiliar.guia_remision.EstadoGuiaQueryDTO;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.auxiliar.guia_remision.guia_remision_remitente.MotivoTrasladoQueryDTO;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.guia_remision.seccion.DestinatarioQueryDTO;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.guia_remision.seccion.DireccionSimpleQueryDTO;

import java.util.Date;

@Slf4j
@Data
@NoArgsConstructor
public class GuiaRemisionRemitenteSimpleQueryDTO {

    private Integer id;

    private String numero;

    private String serie;

    @JsonProperty("motivoTraslado")
    private MotivoTrasladoQueryDTO envioMotivoTraslado;

    private DestinatarioQueryDTO destinatario;

    private Date fechaEmision;

    private String usuarioEmision;

    private DireccionSimpleQueryDTO puntoPartida;

    private DireccionSimpleQueryDTO puntoLlegada;

    private EstadoGuiaQueryDTO estado;
}
