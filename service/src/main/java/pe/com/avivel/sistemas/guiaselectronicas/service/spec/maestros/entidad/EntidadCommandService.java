package pe.com.avivel.sistemas.guiaselectronicas.service.spec.maestros.entidad;

import pe.com.avivel.sistemas.guiaselectronicas.dto.models.command.maestros.entidad.EntidadAlmacenCommandDTO;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.command.maestros.entidad.EntidadCommandDTO;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.command.maestros.entidad.EntidadCorreoCommandDTO;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.maestros.entidad.EntidadAlmacenQueryDTO;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.maestros.entidad.EntidadCorreoQueryDTO;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.maestros.entidad.EntidadQueryDTO;

public interface EntidadCommandService {

    EntidadQueryDTO create(EntidadCommandDTO dto);

    EntidadQueryDTO update(Integer id, EntidadCommandDTO dto);

    int updateEstadoById(Integer id, boolean activo);

    EntidadAlmacenQueryDTO createAlmacen(EntidadAlmacenCommandDTO dto);

    EntidadAlmacenQueryDTO updateAlmacen(Integer id, EntidadAlmacenCommandDTO dto);

    int updateEstadoAlmacenById(Integer id, boolean activo);

    EntidadCorreoQueryDTO createCorreo(EntidadCorreoCommandDTO dto);

    EntidadCorreoQueryDTO updateCorreo(Integer id, EntidadCorreoCommandDTO dto);

    int updateEstadoCorreoById(Integer id, boolean activo);
}
