package pe.com.avivel.sistemas.guiaselectronicas.service.impl.maestros.entidad;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.maestros.entidad.*;
import pe.com.avivel.sistemas.guiaselectronicas.repository.maestros.entidad.EntidadAlmacenRepository;
import pe.com.avivel.sistemas.guiaselectronicas.repository.maestros.entidad.EntidadCorreoRepository;
import pe.com.avivel.sistemas.guiaselectronicas.repository.maestros.entidad.EntidadRepository;
import pe.com.avivel.sistemas.guiaselectronicas.service.spec.maestros.entidad.EntidadQueryService;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class EntidadQueryServiceImpl implements EntidadQueryService {

    private final EntidadRepository entidadRepository;

    private final EntidadCorreoRepository entidadCorreoRepository;

    private final EntidadAlmacenRepository entidadAlmacenRepository;

    private final ModelMapper modelMapper;

    @Override
    public Page<EntidadQueryDTO> listAllByFiltrosPaginado(FiltrosListadoAllEntidades filtros, Pageable pageable) {
        return entidadRepository.listAllByFiltrosPaginado(filtros, pageable)
                .map(e -> modelMapper.map(e, EntidadQueryDTO.class));
    }

    @Override
    public Page<EntidadQueryDTO> listByFiltrosPaginado(FiltrosListadoEntidades filtros, Pageable pageable) {
        return entidadRepository.listByFiltrosPaginado(filtros, pageable)
                .map(e -> modelMapper.map(e, EntidadQueryDTO.class));
    }

    @Override
    public List<EntidadAlmacenQueryDTO> listAlmacenesByEntidadId(Integer entidadId) {
        return entidadAlmacenRepository.listByEntidadId(entidadId)
                .stream()
                .map(ea -> modelMapper.map(ea, EntidadAlmacenQueryDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public Page<EntidadAlmacenQueryDTO> listAllAlmacenesByFiltrosPaginado(FiltrosListadoAllEntidadAlmacenes filtros, Pageable pageable) {
        return entidadAlmacenRepository.listAllByFiltrosPaginado(filtros, pageable)
                .map(ea -> modelMapper.map(ea, EntidadAlmacenQueryDTO.class));
    }

    @Override
    public List<EntidadCorreoQueryDTO> listCorreosByEntidadId(Integer entidadId) {
        return entidadCorreoRepository.listByEntidadId(entidadId)
                .stream()
                .map(ec -> modelMapper.map(ec, EntidadCorreoQueryDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public Page<EntidadCorreoQueryDTO> listAllCorreosByFiltrosPaginado(FiltrosListadoAllEntidadCorreos filtros, Pageable pageable) {
        return entidadCorreoRepository.listAllByFiltrosPaginado(filtros, pageable)
                .map(ec -> modelMapper.map(ec, EntidadCorreoQueryDTO.class));
    }
}
