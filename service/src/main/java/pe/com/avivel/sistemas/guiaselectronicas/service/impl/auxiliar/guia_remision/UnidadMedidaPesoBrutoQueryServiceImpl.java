package pe.com.avivel.sistemas.guiaselectronicas.service.impl.auxiliar.guia_remision;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.auxiliar.guia_remision.UnidadMedidaPesoBrutoQueryDTO;
import pe.com.avivel.sistemas.guiaselectronicas.repository.auxiliares.guia_remision.UnidadMedidaPesoBrutoRepository;
import pe.com.avivel.sistemas.guiaselectronicas.service.spec.auxiliares.guia_remision.UnidadMedidaPesoBrutoQueryService;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UnidadMedidaPesoBrutoQueryServiceImpl implements UnidadMedidaPesoBrutoQueryService {

    private final UnidadMedidaPesoBrutoRepository unidadMedidaPesoBrutoRepository;

    private final ModelMapper modelMapper;

    @Override
    public List<UnidadMedidaPesoBrutoQueryDTO> list() {
        return unidadMedidaPesoBrutoRepository.list()
                .stream()
                .map(u -> modelMapper.map(u, UnidadMedidaPesoBrutoQueryDTO.class))
                .collect(Collectors.toList());
    }
}
