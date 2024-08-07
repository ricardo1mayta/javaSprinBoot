package pe.com.avivel.sistemas.guiaselectronicas.service.impl.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.security.SesionQueryDTO;
import pe.com.avivel.sistemas.guiaselectronicas.entities.security.Sesion;
import pe.com.avivel.sistemas.guiaselectronicas.repository.security.SesionRepository;
import pe.com.avivel.sistemas.guiaselectronicas.service.spec.security.SesionQueryService;

import java.util.Date;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SesionQueryServiceImpl implements SesionQueryService {

    private final SesionRepository sesionRepository;

    private final ModelMapper modelMapper;

    @Override
    public Optional<SesionQueryDTO> findByToken(String token) {
        Optional<Sesion> sesionOp = sesionRepository.findByToken(token);
        return sesionOp.map(s -> modelMapper.map(s, SesionQueryDTO.class));
    }

    @Override
    public Page<SesionQueryDTO> listAllByFiltrosPaginado(Integer usuarioId, Date fechaInicio, Date fechaFin, Pageable pageable) {
        return sesionRepository.listAllByFiltrosPaginado(usuarioId, fechaInicio, fechaFin, pageable)
                .map(s -> modelMapper.map(s, SesionQueryDTO.class));
    }
}
