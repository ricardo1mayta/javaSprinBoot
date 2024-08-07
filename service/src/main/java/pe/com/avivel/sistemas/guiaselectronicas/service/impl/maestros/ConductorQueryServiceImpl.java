package pe.com.avivel.sistemas.guiaselectronicas.service.impl.maestros;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.maestros.ConductorQueryDTO;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.maestros.FiltrosListadoAllConductores;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.maestros.FiltrosListadoConductores;
import pe.com.avivel.sistemas.guiaselectronicas.repository.maestros.ConductorRepository;
import pe.com.avivel.sistemas.guiaselectronicas.service.spec.maestros.ConductorQueryService;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConductorQueryServiceImpl implements ConductorQueryService {

    private final ConductorRepository conductorRepository;

    private final ModelMapper modelMapper;

    @Override
    public Page<ConductorQueryDTO> listAllByFiltrosPaginado(FiltrosListadoAllConductores filtros, Pageable pageable) {
        return conductorRepository.listAllByFiltrosPaginado(filtros, pageable)
                .map(c -> modelMapper.map(c, ConductorQueryDTO.class));
    }

    @Override
    public Page<ConductorQueryDTO> listByFiltrosPaginado(FiltrosListadoConductores filtros, Pageable pageable) {
        return conductorRepository.listByFiltrosPaginado(filtros, pageable)
                .map(c -> modelMapper.map(c, ConductorQueryDTO.class));
    }
}