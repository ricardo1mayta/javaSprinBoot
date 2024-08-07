package pe.com.avivel.sistemas.guiaselectronicas.service.spec.ubigeos;

import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.ubigeos.DepartamentoQueryDTO;

import java.util.List;

public interface DepartamentoQueryService {
    List<DepartamentoQueryDTO> listByPaisId(Integer paisId);
}
