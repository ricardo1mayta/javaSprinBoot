package pe.com.avivel.sistemas.guiaselectronicas.service.spec.maestros.vehiculo;

import pe.com.avivel.sistemas.guiaselectronicas.dto.models.command.maestros.vehiculo.VehiculoCommandDTO;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.maestros.vehiculo.VehiculoQueryDTO;

public interface VehiculoCommandService {

    VehiculoQueryDTO create(VehiculoCommandDTO dto);

    VehiculoQueryDTO update(Integer id, VehiculoCommandDTO dto);

    int updateEstadoById(Integer id, boolean activo);
}
