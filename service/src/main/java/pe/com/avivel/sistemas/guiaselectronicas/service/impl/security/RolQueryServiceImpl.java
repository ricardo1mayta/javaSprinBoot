package pe.com.avivel.sistemas.guiaselectronicas.service.impl.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.security.RolQueryDTO;
import pe.com.avivel.sistemas.guiaselectronicas.entities.security.RolTipo;
import pe.com.avivel.sistemas.guiaselectronicas.entities.util.EstadoGeneral;
import pe.com.avivel.sistemas.guiaselectronicas.repository.security.RolRepository;
import pe.com.avivel.sistemas.guiaselectronicas.service.spec.security.RolQueryService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RolQueryServiceImpl implements RolQueryService {

    private final RolRepository rolRepository;

    private final ModelMapper modelMapper;

    @Override
    public Optional<RolQueryDTO> findById(Integer id) {
        return rolRepository.findById(id).map(r -> modelMapper.map(r, RolQueryDTO.class));
    }

    @Override
    public List<RolQueryDTO> listByRolTipo(RolTipo tipo) {
        return rolRepository.listByRolTipo(tipo)
                .stream()
                .map(r -> modelMapper.map(r, RolQueryDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<RolQueryDTO> listByUsuarioId(Integer usuarioId) {
        return rolRepository.listByUsuarioId(usuarioId)
                .stream()
                .map(r -> modelMapper.map(r, RolQueryDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public Page<RolQueryDTO> listAllByFiltrosPaginado(String nombre, EstadoGeneral estado, Pageable pageable) {
        return rolRepository.listAllByFiltrosPaginado(nombre, estado, pageable)
                .map(r -> modelMapper.map(r, RolQueryDTO.class));
    }
}
