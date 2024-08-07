package pe.com.avivel.sistemas.guiaselectronicas.service.impl.maestros.vehiculo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.maestros.vehiculo.MarcaQueryDTO;
import pe.com.avivel.sistemas.guiaselectronicas.repository.maestros.vehiculo.MarcaRepository;
import pe.com.avivel.sistemas.guiaselectronicas.service.spec.auxiliares.guia_remision.guia_remision_remitente.MotivoTrasladoQueryService;
import pe.com.avivel.sistemas.guiaselectronicas.service.spec.maestros.vehiculo.MarcaQueryService;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MarcaQueryServiceImpl implements MarcaQueryService {

    private final MarcaRepository marcaRepository;

    private final ModelMapper modelMapper;

    @Override
    public List<MarcaQueryDTO> list() {
        return marcaRepository.list()
                .stream()
                .map(m -> modelMapper.map(m, MarcaQueryDTO.class))
                .collect(Collectors.toList());
    }
}
