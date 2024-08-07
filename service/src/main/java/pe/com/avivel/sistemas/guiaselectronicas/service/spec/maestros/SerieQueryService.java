package pe.com.avivel.sistemas.guiaselectronicas.service.spec.maestros;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.maestros.FiltrosListadoAllSeries;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.maestros.SerieQueryDTO;

import java.util.List;

public interface SerieQueryService {

    List<SerieQueryDTO> list();

    Page<SerieQueryDTO> listAllByFiltrosPaginado(FiltrosListadoAllSeries filtros, Pageable pageable);

    String findCorrelativoActual(Integer serieId);
}
