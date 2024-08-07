package pe.com.avivel.sistemas.guiaselectronicas.service.spec.maestros;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.maestros.AlmacenQueryDTO;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.maestros.FiltrosListadoAllAlmacenes;

import java.util.List;

public interface AlmacenQueryService {

    List<AlmacenQueryDTO> list();

    Page<AlmacenQueryDTO> listAllByFiltrosPaginado(FiltrosListadoAllAlmacenes filtros, Pageable pageable);
}
