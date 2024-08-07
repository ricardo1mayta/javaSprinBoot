package pe.com.avivel.sistemas.guiaselectronicas.service.spec.guia_remision.guia_remision_remitente;

import pe.com.avivel.sistemas.guiaselectronicas.dto.models.command.guia_remision.jventas.GuiaRemisionBodyDTO;
import pe.com.avivel.sistemas.guiaselectronicas.entities.guia_remision.guia_remision_remitente.GuiaRemisionRemitente;


public interface GuiaRemisionRemitenteJVentasCommandService {

    GuiaRemisionRemitente create(GuiaRemisionBodyDTO guia);

}
