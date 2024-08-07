package pe.com.avivel.sistemas.guiaselectronicas.restapi.controller.query.maestros;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.maestros.ArticuloQueryDTO;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.maestros.ArticuloUnidadMedidaQueryDTO;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.maestros.FiltrosListadoAllArticulos;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.maestros.FiltrosListadoArticulos;
import pe.com.avivel.sistemas.guiaselectronicas.restapi.response.ResponseData;
import pe.com.avivel.sistemas.guiaselectronicas.service.spec.maestros.ArticuloQueryService;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import java.util.List;

import static pe.com.avivel.sistemas.guiaselectronicas.restapi.auth.UrlConfig.ArticuloUrl.Query.*;
import static pe.com.avivel.sistemas.guiaselectronicas.restapi.auth.UrlConfig.ArticuloUrl.TAG;

//TODO: Revisar integracion con REDIS para cache de las consultas
@Slf4j
@Tag(name = TAG)
@RequestMapping(value = PREFIX, produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@RequiredArgsConstructor
public class ArticuloQueryController {

    private final ArticuloQueryService articuloQueryService;

    @PreAuthorize("hasAnyRole('_ADMIN', '_ALMACEN_JEFATURA', '_ALMACEN')")
    @Operation(summary = "Obtiene un listado de todos los articulos de manera paginada", tags = TAG)
    @PostMapping(value = LIST_ALL_BY_FILTROS_PAGINADO)
    public ResponseEntity<?> listAllByFiltrosPaginado(
            @Valid @RequestBody FiltrosListadoAllArticulos filtros,
            @PageableDefault(sort = "id", direction = Sort.Direction.DESC) @ParameterObject Pageable pageable ) {
        Page<ArticuloQueryDTO> articulos = articuloQueryService.listAllByFiltrosPaginado(filtros, pageable);
        return ResponseEntity.ok(new ResponseData<>(articulos));
    }

    @PreAuthorize("hasAnyRole('_ADMIN', '_ALMACEN_JEFATURA', '_ALMACEN')")
    @Operation(summary = "Obtiene un listado de las unidades asignadas a un articulos", tags = TAG)
    @GetMapping(value = LIST_ALL_UNIDADES_MEDIDA_BY_ARTICULO)
    public ResponseEntity<?> listAutocompleteByDescripcion(@PathVariable("articuloId") Integer articuloId) {
        List<ArticuloUnidadMedidaQueryDTO> articuloUnidadesMedida = articuloQueryService.listAllUnidadesMedidaByArticuloId(articuloId);
        return ResponseEntity.ok(new ResponseData<>(articuloUnidadesMedida));
    }

    @PreAuthorize("hasAnyRole('_ADMIN', '_ALMACEN_JEFATURA', '_ALMACEN', '_OPERARIO')")
    @Operation(summary = "Obtiene un listado de los articulos activos de manera paginada", tags = TAG)
    @PostMapping(value = LIST_BY_FILTROS_PAGINADO)
    public ResponseEntity<?> listByFiltrosPaginado(
            @Valid @RequestBody FiltrosListadoArticulos filtros,
            @PageableDefault(sort = "id", direction = Sort.Direction.DESC) @ParameterObject Pageable pageable ) {
        Page<ArticuloQueryDTO> articulos = articuloQueryService.listByFiltrosPaginado(filtros, pageable);
        return ResponseEntity.ok(new ResponseData<>(articulos));
    }

    @PreAuthorize("hasAnyRole('_ADMIN', '_ALMACEN_JEFATURA', '_ALMACEN', '_OPERARIO')")
    @Operation(summary = "Obtiene un top de articulos activos que coinciden con el codigo ingresado", tags = TAG)
    @GetMapping(value = LIST_AUTOCOMPLETE_BY_CODIGO)
    public ResponseEntity<?> listAutocompleteByCodigo(@Valid @NotBlank @PathVariable("codigo") String codigo) {
        List<ArticuloQueryDTO> articulos = articuloQueryService.listAutocompleteByCodigo(codigo);
        return ResponseEntity.ok(new ResponseData<>(articulos));
    }

    @PreAuthorize("hasAnyRole('_ADMIN', '_ALMACEN_JEFATURA', '_ALMACEN', '_OPERARIO')")
    @Operation(summary = "Obtiene un top de articulos activos que coinciden con la descripcion ingresada", tags = TAG)
    @GetMapping(value = LIST_AUTOCOMPLETE_BY_DESCRIPCION)
    public ResponseEntity<?> listAutocompleteByDescripcion(@Valid @NotBlank @PathVariable("descripcion") String descripcion) {
        List<ArticuloQueryDTO> articulos = articuloQueryService.listAutocompleteByDescripcion(descripcion);
        return ResponseEntity.ok(new ResponseData<>(articulos));
    }
}
