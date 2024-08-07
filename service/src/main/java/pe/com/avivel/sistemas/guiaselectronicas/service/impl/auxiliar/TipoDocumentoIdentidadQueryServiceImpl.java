package pe.com.avivel.sistemas.guiaselectronicas.service.impl.auxiliar;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.auxiliar.TipoDocumentoIdentidadQueryDTO;
import pe.com.avivel.sistemas.guiaselectronicas.repository.auxiliares.TipoDocumentoIdentidadRepository;
import pe.com.avivel.sistemas.guiaselectronicas.service.spec.auxiliares.TipoDocumentoIdentidadQueryService;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TipoDocumentoIdentidadQueryServiceImpl implements TipoDocumentoIdentidadQueryService {

    private final TipoDocumentoIdentidadRepository tipoDocumentoIdentidadRepository;

    private final ModelMapper modelMapper;

    @Override
    public List<TipoDocumentoIdentidadQueryDTO> list() {
        return tipoDocumentoIdentidadRepository.list()
                .stream()
                .map(t -> modelMapper.map(t, TipoDocumentoIdentidadQueryDTO.class))
                .collect(Collectors.toList());
    }
}
