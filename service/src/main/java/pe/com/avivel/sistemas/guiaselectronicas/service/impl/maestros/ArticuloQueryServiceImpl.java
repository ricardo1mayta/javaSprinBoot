package pe.com.avivel.sistemas.guiaselectronicas.service.impl.maestros;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.maestros.ArticuloQueryDTO;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.maestros.ArticuloUnidadMedidaQueryDTO;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.maestros.FiltrosListadoAllArticulos;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.maestros.FiltrosListadoArticulos;
import pe.com.avivel.sistemas.guiaselectronicas.repository.maestros.ArticuloRepository;
import pe.com.avivel.sistemas.guiaselectronicas.repository.maestros.ArticuloUnidadMedidaRepository;
import pe.com.avivel.sistemas.guiaselectronicas.service.spec.maestros.ArticuloQueryService;

import java.util.List;
import java.util.stream.Collectors;

import static pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.maestros.ArticuloQueryDTO.TYPE_MAP_ARTICULOS_SIN_UNIDADES;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArticuloQueryServiceImpl implements ArticuloQueryService {

    private final ArticuloRepository articuloRepository;

    private final ArticuloUnidadMedidaRepository articuloUnidadMedidaRepository;

    private final ModelMapper modelMapper;

    @Value("${app.pagination.items-autocomplete}")
    private Integer NRO_ITEMS_AUTOCOMPLETE;

    @Override
    public Page<ArticuloQueryDTO> listAllByFiltrosPaginado(FiltrosListadoAllArticulos filtros, Pageable pageable) {
        return articuloRepository.listAllByFiltrosPaginado(filtros, pageable)
                .map(a -> modelMapper.map(a, ArticuloQueryDTO.class, TYPE_MAP_ARTICULOS_SIN_UNIDADES));
    }

    @Override
    public Page<ArticuloQueryDTO> listByFiltrosPaginado(FiltrosListadoArticulos filtros, Pageable pageable) {
        return articuloRepository.listByFiltrosPaginado(filtros, pageable)
                .map(a -> modelMapper.map(a, ArticuloQueryDTO.class));
    }

    @Override
    public List<ArticuloQueryDTO> listAutocompleteByCodigo(String codigo) {
        FiltrosListadoArticulos filtros = FiltrosListadoArticulos
                .builder()
                .codigo(codigo)
                .descripcion("")
                .build();

        Pageable pageable = PageRequest.of(0, NRO_ITEMS_AUTOCOMPLETE, Sort.by("id").descending());
        return listByFiltrosPaginado(filtros, pageable).getContent();
    }

    @Override
    public List<ArticuloQueryDTO> listAutocompleteByDescripcion(String descripcion) {
        FiltrosListadoArticulos filtros = FiltrosListadoArticulos
                .builder()
                .codigo("")
                .descripcion(descripcion)
                .build();

        Pageable pageable = PageRequest.of(0, NRO_ITEMS_AUTOCOMPLETE, Sort.by("id").descending());
        return listByFiltrosPaginado(filtros, pageable).getContent();
    }

    @Override
    public List<ArticuloUnidadMedidaQueryDTO> listAllUnidadesMedidaByArticuloId(Integer articuloId) {
        return articuloUnidadMedidaRepository.listAllByArticuloId(articuloId)
                .stream()
                .map(aum -> modelMapper.map(aum, ArticuloUnidadMedidaQueryDTO.class))
                .collect(Collectors.toList());
    }
}
