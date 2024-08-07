package pe.com.avivel.sistemas.guiaselectronicas.service.spec.auxiliares.guia_remision.guia_remision_remitente;

import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.auxiliar.guia_remision.guia_remision_remitente.ModalidadTrasladoQueryDTO;

import java.util.List;

public interface ModalidadTrasladoQueryService {
    List<ModalidadTrasladoQueryDTO> list();
}
