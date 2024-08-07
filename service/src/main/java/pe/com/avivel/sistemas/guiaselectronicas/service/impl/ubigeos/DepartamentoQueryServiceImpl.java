package pe.com.avivel.sistemas.guiaselectronicas.service.impl.ubigeos;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.ubigeos.DepartamentoQueryDTO;
import pe.com.avivel.sistemas.guiaselectronicas.repository.ubigeos.DepartamentoRepository;
import pe.com.avivel.sistemas.guiaselectronicas.service.spec.ubigeos.DepartamentoQueryService;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class DepartamentoQueryServiceImpl implements DepartamentoQueryService {

    private final DepartamentoRepository departamentoRepository;

    private final ModelMapper modelMapper;

    @Override
    public List<DepartamentoQueryDTO> listByPaisId(Integer paisId) {
        return departamentoRepository.listByPaisId(paisId)
                .stream()
                .map(d -> modelMapper.map(d, DepartamentoQueryDTO.class))
                .collect(Collectors.toList());
    }
}
