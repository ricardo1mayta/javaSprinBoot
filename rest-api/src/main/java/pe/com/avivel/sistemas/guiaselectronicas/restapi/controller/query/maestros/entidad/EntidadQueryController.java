package pe.com.avivel.sistemas.guiaselectronicas.restapi.controller.query.maestros.entidad;

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
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.maestros.entidad.*;
import pe.com.avivel.sistemas.guiaselectronicas.restapi.response.ResponseData;
import pe.com.avivel.sistemas.guiaselectronicas.service.spec.maestros.entidad.EntidadQueryService;

import javax.validation.Valid;

import java.util.List;

import static pe.com.avivel.sistemas.guiaselectronicas.restapi.auth.UrlConfig.EntidadUrl.Query.*;
import static pe.com.avivel.sistemas.guiaselectronicas.restapi.auth.UrlConfig.EntidadUrl.TAG;

@Slf4j
@Tag(name = TAG)
@RequestMapping(value = PREFIX, produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@RequiredArgsConstructor
public class EntidadQueryController {

    private final EntidadQueryService entidadQueryService;

    @PreAuthorize("hasAnyRole('_ADMIN', '_ALMACEN_JEFATURA', '_ALMACEN')")
    @Operation(summary = "Obtiene un listado de todas las entidades de manera paginada", tags = TAG)
    @PostMapping(value = LIST_ALL_BY_FILTROS_PAGINADO)
    public ResponseEntity<?> listAllByFiltrosPaginado(
            @Valid @RequestBody FiltrosListadoAllEntidades filtros,
            @PageableDefault(sort = "id", direction = Sort.Direction.DESC) @ParameterObject Pageable pageable ) {
        Page<EntidadQueryDTO> entidades = entidadQueryService.listAllByFiltrosPaginado(filtros, pageable);
        return ResponseEntity.ok(new ResponseData<>(entidades));
    }

    @PreAuthorize("hasAnyRole('_ADMIN', '_ALMACEN_JEFATURA', '_ALMACEN', '_OPERARIO')")
    @Operation(summary = "Obtiene un listado de las entidades activas de manera paginada", tags = TAG)
    @PostMapping(value = LIST_BY_FILTROS_PAGINADO)
    public ResponseEntity<?> listByFiltrosPaginado(
            @Valid @RequestBody FiltrosListadoEntidades filtros,
            @PageableDefault(sort = "id", direction = Sort.Direction.DESC) @ParameterObject Pageable pageable ) {
        Page<EntidadQueryDTO> entidades = entidadQueryService.listByFiltrosPaginado(filtros, pageable);
        return ResponseEntity.ok(new ResponseData<>(entidades));
    }

    @PreAuthorize("hasAnyRole('_ADMIN', '_ALMACEN_JEFATURA', '_ALMACEN', '_OPERARIO')")
    @Operation(summary = "Obtiene un listado de los almacenes activos asociados a una entidad", tags = TAG)
    @GetMapping(value = LIST_ALMACENES_BY_ID)
    public ResponseEntity<?> listAlmacenesByEntidadId(@PathVariable("id") Integer id ) {
        List<EntidadAlmacenQueryDTO> almacenes = entidadQueryService.listAlmacenesByEntidadId(id);
        return ResponseEntity.ok(new ResponseData<>(almacenes));
    }

    @PreAuthorize("hasAnyRole('_ADMIN', '_ALMACEN_JEFATURA', '_ALMACEN')")
    @Operation(summary = "Obtiene un listado de todos los almacenes de una entidad de manera paginada", tags = TAG)
    @PostMapping(value = LIST_ALL_ALMACENES_BY_FILTROS_PAGINADO)
    public ResponseEntity<?> listAllAlmacenesByFiltrosPaginado(
            @Valid @RequestBody FiltrosListadoAllEntidadAlmacenes filtros,
            @PageableDefault(sort = "id", direction = Sort.Direction.DESC) @ParameterObject Pageable pageable ) {
        Page<EntidadAlmacenQueryDTO> almacenes = entidadQueryService.listAllAlmacenesByFiltrosPaginado(filtros, pageable);
        return ResponseEntity.ok(new ResponseData<>(almacenes));
    }

    @PreAuthorize("hasAnyRole('_ADMIN', '_ALMACEN_JEFATURA', '_ALMACEN', '_OPERARIO')")
    @Operation(summary = "Obtiene un listado de los correos activos asociados a una entidad", tags = TAG)
    @GetMapping(value = LIST_CORREOS_BY_ID)
    public ResponseEntity<?> listCorreosByEntidadId(@PathVariable("id") Integer id ) {
        List<EntidadCorreoQueryDTO> correos = entidadQueryService.listCorreosByEntidadId(id);
        return ResponseEntity.ok(new ResponseData<>(correos));
    }

    @PreAuthorize("hasAnyRole('_ADMIN', '_ALMACEN_JEFATURA', '_ALMACEN')")
    @Operation(summary = "Obtiene un listado de todos los correos de una entidad de manera paginada", tags = TAG)
    @PostMapping(value = LIST_ALL_CORREOS_BY_FILTROS_PAGINADO)
    public ResponseEntity<?> listAllCorreosByFiltrosPaginado(
            @Valid @RequestBody FiltrosListadoAllEntidadCorreos filtros,
            @PageableDefault(sort = "id", direction = Sort.Direction.DESC) @ParameterObject Pageable pageable ) {
        Page<EntidadCorreoQueryDTO> correos = entidadQueryService.listAllCorreosByFiltrosPaginado(filtros, pageable);
        return ResponseEntity.ok(new ResponseData<>(correos));
    }
}
