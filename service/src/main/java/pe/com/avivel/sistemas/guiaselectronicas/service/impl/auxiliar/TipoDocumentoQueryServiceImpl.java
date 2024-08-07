package pe.com.avivel.sistemas.guiaselectronicas.service.impl.auxiliar;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.auxiliar.TipoDocumentoQueryDTO;
import pe.com.avivel.sistemas.guiaselectronicas.repository.auxiliares.TipoDocumentoRepository;
import pe.com.avivel.sistemas.guiaselectronicas.service.spec.auxiliares.TipoDocumentoQueryService;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TipoDocumentoQueryServiceImpl implements TipoDocumentoQueryService {

    private final TipoDocumentoRepository tipoDocumentoRepository;

    private final ModelMapper modelMapper;

    @Override
    public List<TipoDocumentoQueryDTO> list() {
        return tipoDocumentoRepository.list()
                .stream()
                .map(t -> modelMapper.map(t, TipoDocumentoQueryDTO.class))
                .collect(Collectors.toList());
    }
}
