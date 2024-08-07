package pe.com.avivel.sistemas.guiaselectronicas.service.spec.guia_remision.guia_remision_remitente;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.guia_remision.guia_remision_remitente.FiltrosListadoGuias;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.guia_remision.guia_remision_remitente.FiltrosReporteGuiasElaboradas;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.guia_remision.guia_remision_remitente.GuiaRemisionRemitenteDetalladoQueryDTO;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.guia_remision.guia_remision_remitente.GuiaRemisionRemitenteSimpleQueryDTO;

import java.util.Optional;

public interface GuiaRemisionRemitenteQueryService {

    Optional<GuiaRemisionRemitenteDetalladoQueryDTO> findById(Integer id);

    Page<GuiaRemisionRemitenteSimpleQueryDTO> listByFiltrosPaginados(FiltrosListadoGuias filtros, Pageable pageable);

    Page<GuiaRemisionRemitenteSimpleQueryDTO> listReporteGuiasElaboradasByFiltrosPaginados(FiltrosReporteGuiasElaboradas filtros, Pageable pageable);
}
