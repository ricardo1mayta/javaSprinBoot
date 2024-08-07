package pe.com.avivel.sistemas.guiaselectronicas.service.spec.maestros;

import pe.com.avivel.sistemas.guiaselectronicas.dto.models.command.maestros.ConductorCommandDTO;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.maestros.ConductorQueryDTO;

public interface ConductorCommandService {
    ConductorQueryDTO create(ConductorCommandDTO dto);

    ConductorQueryDTO update(Integer id, ConductorCommandDTO dto);

    int updateEstadoById(Integer id, boolean activo);
}
