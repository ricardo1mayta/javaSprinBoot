package pe.com.avivel.sistemas.guiaselectronicas.service.spec.ubigeos;

import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.ubigeos.ProvinciaQueryDTO;

import java.util.List;

public interface ProvinciaQueryService {
    List<ProvinciaQueryDTO> listByDepartamentoId(Integer departamentoId);
}
