package pe.com.avivel.sistemas.guiaselectronicas.service.spec.ubigeos;

import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.ubigeos.DistritoQueryDTO;

import java.util.List;

public interface DistritoQueryService {
    List<DistritoQueryDTO> listByProvinciaId(Integer provinciaId);
}
