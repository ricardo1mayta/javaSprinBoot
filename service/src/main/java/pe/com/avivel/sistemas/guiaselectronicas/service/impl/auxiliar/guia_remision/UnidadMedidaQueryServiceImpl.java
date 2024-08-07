package pe.com.avivel.sistemas.guiaselectronicas.service.impl.auxiliar.guia_remision;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.auxiliar.guia_remision.UnidadMedidaQueryDTO;
import pe.com.avivel.sistemas.guiaselectronicas.repository.auxiliares.guia_remision.UnidadMedidaRepository;
import pe.com.avivel.sistemas.guiaselectronicas.service.spec.auxiliares.guia_remision.UnidadMedidaQueryService;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UnidadMedidaQueryServiceImpl implements UnidadMedidaQueryService {

    private final UnidadMedidaRepository unidadMedidaRepository;

    private final ModelMapper modelMapper;

    @Override
    public List<UnidadMedidaQueryDTO> list() {
        return unidadMedidaRepository.list()
                .stream()
                .map(u -> modelMapper.map(u, UnidadMedidaQueryDTO.class))
                .collect(Collectors.toList());
    }
}
