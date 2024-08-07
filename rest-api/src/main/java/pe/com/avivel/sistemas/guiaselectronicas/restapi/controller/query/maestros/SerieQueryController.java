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
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.maestros.FiltrosListadoAllSeries;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.maestros.SerieQueryDTO;
import pe.com.avivel.sistemas.guiaselectronicas.restapi.response.ResponseData;
import pe.com.avivel.sistemas.guiaselectronicas.service.spec.maestros.SerieQueryService;

import javax.validation.Valid;
import java.util.List;

import static pe.com.avivel.sistemas.guiaselectronicas.restapi.auth.UrlConfig.SerieUrl.TAG;
import static pe.com.avivel.sistemas.guiaselectronicas.restapi.auth.UrlConfig.SerieUrl.Query.*;

@Slf4j
@Tag(name = TAG)
@RequestMapping(value = PREFIX, produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@RequiredArgsConstructor
public class SerieQueryController {

    private final SerieQueryService serieQueryService;

    @PreAuthorize("hasAnyRole('_ADMIN','_ALMACEN_JEFATURA', '_ALMACEN', '_OPERARIO')")
    @Operation(summary = "Obtiene un listado de las series activas", tags = TAG)
    @GetMapping(value = LIST)
    public ResponseEntity<?> listSeriesDisponibles() {
        List<SerieQueryDTO> series = serieQueryService.list();
        return ResponseEntity.ok(new ResponseData<>(series));
    }

    @PreAuthorize("hasAnyRole('_ADMIN','_CONTABILIDAD')")
    @Operation(summary = "Obtiene un listado de todos las series de manera paginada", tags = TAG)
    @PostMapping(value = LIST_ALL_BY_FILTROS_PAGINADO)
    public ResponseEntity<?> listAllByFiltrosPaginado(
            @Valid @RequestBody FiltrosListadoAllSeries filtros,
            @PageableDefault(sort = "id", direction = Sort.Direction.DESC) @ParameterObject Pageable pageable ) {
        Page<SerieQueryDTO> series = serieQueryService.listAllByFiltrosPaginado(filtros, pageable);
        return ResponseEntity.ok(new ResponseData<>(series));
    }

    @PreAuthorize("hasAnyRole('_ADMIN', '_ALMACEN_JEFATURA', '_ALMACEN', '_OPERARIO')")
    @Operation(summary = "Obtiene el correlativo actual de una serie", tags = TAG)
    @PostMapping(value = FIND_CORRELATIVO_ACTUAL)
    public ResponseEntity<?> findCorrelativoActual(@PathVariable("id") Integer id){
        String correlativo =  serieQueryService.findCorrelativoActual(id);
        return ResponseEntity.ok(new ResponseData<>(correlativo));
    }
}
