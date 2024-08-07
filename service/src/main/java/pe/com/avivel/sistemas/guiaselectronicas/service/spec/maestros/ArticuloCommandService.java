package pe.com.avivel.sistemas.guiaselectronicas.service.spec.maestros;

import pe.com.avivel.sistemas.guiaselectronicas.dto.models.command.maestros.ArticuloCommandDTO;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.maestros.ArticuloQueryDTO;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.maestros.ArticuloUnidadMedidaQueryDTO;

public interface ArticuloCommandService {

    ArticuloQueryDTO create(ArticuloCommandDTO dto);

    ArticuloQueryDTO update(Integer id, ArticuloCommandDTO dto);

    int updateEstadoById(Integer id, boolean activo);

    ArticuloUnidadMedidaQueryDTO createUnidadMedida(Integer articuloId, Integer unidadMedidaId);

    void deleteUnidadMedida(Integer articuloUnidadMedidaId);
}
