package pe.com.avivel.sistemas.guiaselectronicas.service.spec.auxiliares;

import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.auxiliar.TipoDocumentoIdentidadQueryDTO;

import java.util.List;

public interface TipoDocumentoIdentidadQueryService {
    List<TipoDocumentoIdentidadQueryDTO> list();
}
