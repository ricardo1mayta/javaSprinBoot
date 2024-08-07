package pe.com.avivel.sistemas.guiaselectronicas.service.spec.security;

import pe.com.avivel.sistemas.guiaselectronicas.dto.models.command.security.SesionCommandDTO;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.security.SesionQueryDTO;

public interface SesionCommandService {
    SesionQueryDTO create(SesionCommandDTO sesionCommandDTO);

    void deleteById(Integer id);
}
