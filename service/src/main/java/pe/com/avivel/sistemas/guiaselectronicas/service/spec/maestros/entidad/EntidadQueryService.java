package pe.com.avivel.sistemas.guiaselectronicas.service.spec.maestros.entidad;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.maestros.entidad.*;

import java.util.List;

public interface EntidadQueryService {

    Page<EntidadQueryDTO> listAllByFiltrosPaginado(FiltrosListadoAllEntidades filtros, Pageable pageable);

    Page<EntidadQueryDTO> listByFiltrosPaginado(FiltrosListadoEntidades filtros, Pageable pageable);

    List<EntidadAlmacenQueryDTO> listAlmacenesByEntidadId(Integer entidadId);

    Page<EntidadAlmacenQueryDTO> listAllAlmacenesByFiltrosPaginado(FiltrosListadoAllEntidadAlmacenes filtros, Pageable pageable);

    List<EntidadCorreoQueryDTO> listCorreosByEntidadId(Integer entidadId);

    Page<EntidadCorreoQueryDTO> listAllCorreosByFiltrosPaginado(FiltrosListadoAllEntidadCorreos filtros, Pageable pageable);
}
