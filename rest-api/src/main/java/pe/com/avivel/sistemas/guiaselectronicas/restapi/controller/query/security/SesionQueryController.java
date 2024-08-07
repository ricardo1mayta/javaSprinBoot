package pe.com.avivel.sistemas.guiaselectronicas.restapi.controller.query.security;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pe.com.avivel.sistemas.guiaselectronicas.commons.util.Util;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.security.SesionQueryDTO;
import pe.com.avivel.sistemas.guiaselectronicas.restapi.response.ResponseData;
import pe.com.avivel.sistemas.guiaselectronicas.service.spec.security.SesionQueryService;

import java.util.Date;

import static pe.com.avivel.sistemas.guiaselectronicas.restapi.auth.UrlConfig.SesionUrl.TAG;
import static pe.com.avivel.sistemas.guiaselectronicas.restapi.auth.UrlConfig.SesionUrl.Query.*;

@Slf4j
@Tag(name = TAG)
@RequestMapping(value = PREFIX, produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@RequiredArgsConstructor
public class SesionQueryController {

    private final SesionQueryService sesionQueryService;

    @PreAuthorize("hasAnyRole('_ADMIN')")
    @Operation(summary = "Obtiene un listado de los sesiones de un usuario de manera paginada.", tags = TAG)
    @GetMapping(value = LIST_ALL_BY_FILTROS_PAGINADO)
    public ResponseEntity<?> listAllByFiltrosPaginados(
            @RequestParam(name = "usuarioId") Integer usuarioId,
            @RequestParam(name = "fecha_inicio", required = false) @DateTimeFormat(pattern= Util.ISO_DATE_PATTERN) Date fechaInicio,
            @RequestParam(name = "fecha_fin", required = false) @DateTimeFormat(pattern= Util.ISO_DATE_PATTERN) Date fechaFin,
            @PageableDefault(sort = "id", direction = Sort.Direction.DESC) @ParameterObject Pageable pageable
    ) {
        Page<SesionQueryDTO> sesiones = sesionQueryService.listAllByFiltrosPaginado(usuarioId, fechaInicio, fechaFin, pageable);
        return ResponseEntity.ok(new ResponseData<>(sesiones));
    }

}
