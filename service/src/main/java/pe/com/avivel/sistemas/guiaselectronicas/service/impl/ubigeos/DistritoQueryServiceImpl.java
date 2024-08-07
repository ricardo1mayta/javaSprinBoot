package pe.com.avivel.sistemas.guiaselectronicas.service.impl.ubigeos;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.ubigeos.DistritoQueryDTO;
import pe.com.avivel.sistemas.guiaselectronicas.repository.ubigeos.DistritoRepository;
import pe.com.avivel.sistemas.guiaselectronicas.service.spec.ubigeos.DistritoQueryService;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class DistritoQueryServiceImpl implements DistritoQueryService {

    private final DistritoRepository distritoRepository;

    private final ModelMapper modelMapper;

    @Override
    public List<DistritoQueryDTO> listByProvinciaId(Integer provinciaId) {
        return distritoRepository.listByProvinciaId(provinciaId)
                .stream()
                .map(p -> modelMapper.map(p, DistritoQueryDTO.class))
                .collect(Collectors.toList());
    }
}
