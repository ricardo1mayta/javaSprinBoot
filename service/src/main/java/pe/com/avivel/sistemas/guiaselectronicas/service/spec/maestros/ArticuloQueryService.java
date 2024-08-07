package pe.com.avivel.sistemas.guiaselectronicas.service.spec.maestros;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.maestros.ArticuloQueryDTO;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.maestros.ArticuloUnidadMedidaQueryDTO;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.maestros.FiltrosListadoAllArticulos;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.maestros.FiltrosListadoArticulos;

import java.util.List;

public interface ArticuloQueryService {

    Page<ArticuloQueryDTO> listAllByFiltrosPaginado(FiltrosListadoAllArticulos filtros, Pageable pageable);

    Page<ArticuloQueryDTO> listByFiltrosPaginado(FiltrosListadoArticulos filtros, Pageable pageable);

    List<ArticuloQueryDTO> listAutocompleteByCodigo(String codigo);

    List<ArticuloQueryDTO> listAutocompleteByDescripcion(String descripcion);

    List<ArticuloUnidadMedidaQueryDTO> listAllUnidadesMedidaByArticuloId(Integer articuloId);
}
