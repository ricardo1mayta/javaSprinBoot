package pe.com.avivel.sistemas.guiaselectronicas.service.impl.auxiliar.guia_remision.guia_remision_remitente;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.auxiliar.guia_remision.guia_remision_remitente.MotivoTrasladoQueryDTO;
import pe.com.avivel.sistemas.guiaselectronicas.repository.auxiliares.guia_remision.guia_remision_remitente.MotivoTrasladoRepository;
import pe.com.avivel.sistemas.guiaselectronicas.service.spec.auxiliares.guia_remision.guia_remision_remitente.MotivoTrasladoQueryService;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MotivoTrasladoQueryServiceImpl implements MotivoTrasladoQueryService {

    private final MotivoTrasladoRepository motivoTrasladoRepository;

    private final ModelMapper modelMapper;

    @Override
    public List<MotivoTrasladoQueryDTO> list() {
        return motivoTrasladoRepository.list()
                .stream()
                .map(m -> modelMapper.map(m, MotivoTrasladoQueryDTO.class))
                .collect(Collectors.toList());
    }
}
