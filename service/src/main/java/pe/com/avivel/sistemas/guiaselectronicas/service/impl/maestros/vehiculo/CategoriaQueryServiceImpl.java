package pe.com.avivel.sistemas.guiaselectronicas.service.impl.maestros.vehiculo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.maestros.vehiculo.CategoriaQueryDTO;
import pe.com.avivel.sistemas.guiaselectronicas.repository.maestros.vehiculo.CategoriaRepository;
import pe.com.avivel.sistemas.guiaselectronicas.service.spec.maestros.vehiculo.CategoriaQueryService;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoriaQueryServiceImpl implements CategoriaQueryService {

    private final CategoriaRepository categoriaRepository;

    private final ModelMapper modelMapper;

    @Override
    public List<CategoriaQueryDTO> list() {
        return categoriaRepository.list()
                .stream()
                .map(m -> modelMapper.map(m, CategoriaQueryDTO.class))
                .collect(Collectors.toList());
    }
}
