package pe.com.avivel.sistemas.guiaselectronicas.service.impl.maestros;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.maestros.AlmacenQueryDTO;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.maestros.FiltrosListadoAllAlmacenes;
import pe.com.avivel.sistemas.guiaselectronicas.repository.maestros.AlmacenRepository;
import pe.com.avivel.sistemas.guiaselectronicas.service.spec.maestros.AlmacenQueryService;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AlmacenQueryServiceImpl implements AlmacenQueryService {

    private final AlmacenRepository almacenRepository;

    private final ModelMapper modelMapper;

    @Override
    public List<AlmacenQueryDTO> list() {
        return almacenRepository.list()
                .stream()
                .map(a -> modelMapper.map(a, AlmacenQueryDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public Page<AlmacenQueryDTO> listAllByFiltrosPaginado(FiltrosListadoAllAlmacenes filtros, Pageable pageable) {
        return almacenRepository.listAllByFiltrosPaginado(filtros, pageable)
                .map(a -> modelMapper.map(a, AlmacenQueryDTO.class));
    }
}
