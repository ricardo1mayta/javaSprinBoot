package pe.com.avivel.sistemas.guiaselectronicas.service.spec.maestros;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.maestros.ConductorQueryDTO;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.maestros.FiltrosListadoAllConductores;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.maestros.FiltrosListadoConductores;

public interface ConductorQueryService {

    Page<ConductorQueryDTO> listAllByFiltrosPaginado(FiltrosListadoAllConductores filtros, Pageable pageable);

    Page<ConductorQueryDTO> listByFiltrosPaginado(FiltrosListadoConductores filtros, Pageable pageable);
}
