package pe.com.avivel.sistemas.guiaselectronicas.service.spec.maestros;

import pe.com.avivel.sistemas.guiaselectronicas.dto.models.command.maestros.AlmacenCommandDTO;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.maestros.AlmacenQueryDTO;

public interface AlmacenCommandService {
    AlmacenQueryDTO create(AlmacenCommandDTO dto);

    AlmacenQueryDTO update(Integer id, AlmacenCommandDTO dto);

    int updateEstadoById(Integer id, boolean activo);
}
