package pe.com.avivel.sistemas.guiaselectronicas.service.impl.guia_remision.guia_remision_remitente;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.guia_remision.guia_remision_remitente.FiltrosListadoGuias;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.guia_remision.guia_remision_remitente.FiltrosReporteGuiasElaboradas;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.guia_remision.guia_remision_remitente.GuiaRemisionRemitenteDetalladoQueryDTO;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.guia_remision.guia_remision_remitente.GuiaRemisionRemitenteSimpleQueryDTO;
import pe.com.avivel.sistemas.guiaselectronicas.entities.guia_remision.guia_remision_remitente.GuiaRemisionRemitente;
import pe.com.avivel.sistemas.guiaselectronicas.repository.guia_remision.guia_remision_remitente.GuiaRemisionRemitenteRepository;
import pe.com.avivel.sistemas.guiaselectronicas.service.spec.guia_remision.guia_remision_remitente.GuiaRemisionRemitenteQueryService;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class GuiaRemisionRemitenteQueryServiceImpl implements GuiaRemisionRemitenteQueryService {

    private final GuiaRemisionRemitenteRepository guiaRemisionRemitenteRepository;

    private final ModelMapper modelMapper;

    @Override
    public Optional<GuiaRemisionRemitenteDetalladoQueryDTO> findById(Integer id) {
        Optional<GuiaRemisionRemitente> guiaRemisionRemitenteOp = guiaRemisionRemitenteRepository.findById(id);
        return guiaRemisionRemitenteOp.map(g -> modelMapper.map(g, GuiaRemisionRemitenteDetalladoQueryDTO.class));
    }

    @Override
    public Page<GuiaRemisionRemitenteSimpleQueryDTO> listByFiltrosPaginados(FiltrosListadoGuias filtros, Pageable pageable) {
        return guiaRemisionRemitenteRepository.listByFiltrosPaginados(filtros, pageable)
                .map(g -> modelMapper.map(g, GuiaRemisionRemitenteSimpleQueryDTO.class));
    }

    @Override
    public Page<GuiaRemisionRemitenteSimpleQueryDTO> listReporteGuiasElaboradasByFiltrosPaginados(FiltrosReporteGuiasElaboradas filtros, Pageable pageable) {
        return guiaRemisionRemitenteRepository.listReporteGuiasElaboradasByFiltrosPaginados(filtros, pageable)
                .map(g -> modelMapper.map(g, GuiaRemisionRemitenteSimpleQueryDTO.class));
    }
}
