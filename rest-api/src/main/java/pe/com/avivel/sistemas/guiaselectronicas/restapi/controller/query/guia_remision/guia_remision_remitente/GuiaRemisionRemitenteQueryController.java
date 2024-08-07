package pe.com.avivel.sistemas.guiaselectronicas.restapi.controller.query.guia_remision.guia_remision_remitente;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.guia_remision.guia_remision_remitente.FiltrosListadoGuias;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.guia_remision.guia_remision_remitente.FiltrosReporteGuiasElaboradas;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.guia_remision.guia_remision_remitente.GuiaRemisionRemitenteDetalladoQueryDTO;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.guia_remision.guia_remision_remitente.GuiaRemisionRemitenteSimpleQueryDTO;
import pe.com.avivel.sistemas.guiaselectronicas.restapi.response.ResponseData;
import pe.com.avivel.sistemas.guiaselectronicas.service.spec.guia_remision.guia_remision_remitente.GuiaRemisionRemitenteQueryService;

import javax.validation.Valid;

import java.util.Optional;

import static pe.com.avivel.sistemas.guiaselectronicas.restapi.auth.UrlConfig.GuiaRemisionRemitenteUrl.TAG;
import static pe.com.avivel.sistemas.guiaselectronicas.restapi.auth.UrlConfig.GuiaRemisionRemitenteUrl.Query.*;
import static pe.com.avivel.sistemas.guiaselectronicas.restapi.controller.ConstansResponse.*;

@Slf4j
@Tag(name = TAG)
@RequestMapping(value = PREFIX, produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@RequiredArgsConstructor
public class GuiaRemisionRemitenteQueryController {

    private final GuiaRemisionRemitenteQueryService guiaRemisionRemitenteQueryService;

    @Operation(summary = "Obtiene una guia de remision remitente por id", tags = TAG)
    @PreAuthorize("hasAnyRole('_ADMIN', '_CONTABILIDAD', '_ALMACEN_JEFATURA', '_ALMACEN', '_OPERARIO')")
    @GetMapping(value = FIND_BY_ID)
    public ResponseEntity<?> findById(@PathVariable("id") Integer id ) {
        Optional<GuiaRemisionRemitenteDetalladoQueryDTO> guiaRemisionRemitenteOp = guiaRemisionRemitenteQueryService.findById(id);
        if(guiaRemisionRemitenteOp.isEmpty()){
            return new ResponseEntity<>(new ResponseData<>(NO_DATA, ""), HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(new ResponseData<>(guiaRemisionRemitenteOp.get()));
    }

    @Operation(summary = "Obtiene un listado de guias de remision de manera paginada.", tags = TAG)
    @PreAuthorize("hasAnyRole('_ADMIN', '_CONTABILIDAD', '_ALMACEN_JEFATURA', '_ALMACEN', '_OPERARIO')")
    @PostMapping(value = LIST_BY_FILTROS_PAGINADO)
    public ResponseEntity<?> listByFiltrosAdministracionPaginados(
            @Valid @RequestBody FiltrosListadoGuias filtros,
            @PageableDefault(sort = "id", direction = Sort.Direction.DESC) @ParameterObject Pageable pageable) {
        Page<GuiaRemisionRemitenteSimpleQueryDTO> page = guiaRemisionRemitenteQueryService
                .listByFiltrosPaginados(filtros, pageable);
        return ResponseEntity.ok(new ResponseData<>(page));
    }

    @Operation(summary = "Obtiene un listado de guias de remision de manera paginada a mostrar en el reporte de guias elaboras.", tags = TAG)
    @PreAuthorize("hasAnyRole('_ADMIN', '_CONTABILIDAD', '_ALMACEN_JEFATURA')")
    @PostMapping(value = LIST_REPORT_GUIAS_ELABORADAS_BY_FILTROS_PAGINADO)
    public ResponseEntity<?> listReporteGuiasElaboradasByFiltrosPaginados(
            @Valid @RequestBody FiltrosReporteGuiasElaboradas filtros,
            @PageableDefault(sort = "id", direction = Sort.Direction.DESC) @ParameterObject Pageable pageable) {
        Page<GuiaRemisionRemitenteSimpleQueryDTO> page = guiaRemisionRemitenteQueryService
                .listReporteGuiasElaboradasByFiltrosPaginados(filtros, pageable);
        return ResponseEntity.ok(new ResponseData<>(page));
    }
}
