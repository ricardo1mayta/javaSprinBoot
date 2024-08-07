package pe.com.avivel.sistemas.guiaselectronicas.service.impl.auxiliar.guia_remision.guia_remision_remitente;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.auxiliar.guia_remision.guia_remision_remitente.ModalidadTrasladoQueryDTO;
import pe.com.avivel.sistemas.guiaselectronicas.repository.auxiliares.guia_remision.guia_remision_remitente.ModalidadTrasladoRepository;
import pe.com.avivel.sistemas.guiaselectronicas.service.spec.auxiliares.guia_remision.guia_remision_remitente.ModalidadTrasladoQueryService;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ModalidadTrasladoQueryServiceImpl implements ModalidadTrasladoQueryService {

    private final ModalidadTrasladoRepository modalidadTrasladoRepository;

    private final ModelMapper modelMapper;

    @Override
    public List<ModalidadTrasladoQueryDTO> list() {
        return modalidadTrasladoRepository.list()
                .stream()
                .map(m -> modelMapper.map(m, ModalidadTrasladoQueryDTO.class))
                .collect(Collectors.toList());
    }
}
