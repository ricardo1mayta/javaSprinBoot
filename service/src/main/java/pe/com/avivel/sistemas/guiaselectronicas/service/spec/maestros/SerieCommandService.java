package pe.com.avivel.sistemas.guiaselectronicas.service.spec.maestros;

import pe.com.avivel.sistemas.guiaselectronicas.dto.models.command.maestros.SerieCommandDTO;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.maestros.SerieQueryDTO;

public interface SerieCommandService {

    SerieQueryDTO create(SerieCommandDTO dto);

    SerieQueryDTO update(Integer id, SerieCommandDTO dto);

    int updateEstadoById(Integer id, boolean activo);
}
