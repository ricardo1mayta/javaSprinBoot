package pe.com.avivel.sistemas.guiaselectronicas.service.spec.auxiliares;

import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.auxiliar.TipoDocumentoQueryDTO;

import java.util.List;

public interface TipoDocumentoQueryService {

    List<TipoDocumentoQueryDTO> list();
}
