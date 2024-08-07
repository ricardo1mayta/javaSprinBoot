package pe.com.avivel.sistemas.guiaselectronicas.service.impl.ubigeos;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.ubigeos.ProvinciaQueryDTO;
import pe.com.avivel.sistemas.guiaselectronicas.repository.ubigeos.ProvinciaRepository;
import pe.com.avivel.sistemas.guiaselectronicas.service.spec.ubigeos.ProvinciaQueryService;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProvinciaQueryServiceImpl implements ProvinciaQueryService {

    private final ProvinciaRepository provinciaRepository;

    private final ModelMapper modelMapper;

    @Override
    public List<ProvinciaQueryDTO> listByDepartamentoId(Integer departamentoId) {
        return provinciaRepository.listByDepartamentoId(departamentoId)
                .stream()
                .map(p -> modelMapper.map(p, ProvinciaQueryDTO.class))
                .collect(Collectors.toList());
    }
}
