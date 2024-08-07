package pe.com.avivel.sistemas.guiaselectronicas.restapi.controller.query.maestros.vehiculo;


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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.maestros.vehiculo.FiltrosListadoAllVehiculos;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.maestros.vehiculo.FiltrosListadoVehiculos;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.maestros.vehiculo.VehiculoQueryDTO;
import pe.com.avivel.sistemas.guiaselectronicas.restapi.response.ResponseData;
import pe.com.avivel.sistemas.guiaselectronicas.service.spec.maestros.vehiculo.VehiculoQueryService;

import javax.validation.Valid;

import static pe.com.avivel.sistemas.guiaselectronicas.restapi.auth.UrlConfig.VehiculoUrl.Query.*;
import static pe.com.avivel.sistemas.guiaselectronicas.restapi.auth.UrlConfig.VehiculoUrl.TAG;

@Slf4j
@Tag(name = TAG)
@RequestMapping(value = PREFIX, produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@RequiredArgsConstructor
public class VehiculoQueryController {

    private final VehiculoQueryService vehiculoQueryService;

    @PreAuthorize("hasAnyRole('_ADMIN','_ALMACEN_JEFATURA', '_ALMACEN')")
    @Operation(summary = "Obtiene un listado de todas los vehiculos de manera paginada", tags = TAG)
    @PostMapping(value = LIST_ALL_BY_FILTROS_PAGINADO)
    public ResponseEntity<?> listAllByFiltrosPaginado(
            @Valid @RequestBody FiltrosListadoAllVehiculos filtros,
            @PageableDefault(sort = "id", direction = Sort.Direction.DESC) @ParameterObject Pageable pageable ) {
        Page<VehiculoQueryDTO> vehiculos = vehiculoQueryService.listAllByFiltrosPaginado(filtros, pageable);
        return ResponseEntity.ok(new ResponseData<>(vehiculos));
    }

    @PreAuthorize("hasAnyRole('_ADMIN', '_ALMACEN_JEFATURA', '_ALMACEN')")
    @Operation(summary = "Obtiene un listado de los vehiculos activos de manera paginada", tags = TAG)
    @PostMapping(value = LIST_BY_FILTROS_PAGINADO)
    public ResponseEntity<?> listByFiltrosPaginado(
            @Valid @RequestBody FiltrosListadoVehiculos filtros,
            @PageableDefault(sort = "id", direction = Sort.Direction.DESC) @ParameterObject Pageable pageable ) {
        Page<VehiculoQueryDTO> vehiculos = vehiculoQueryService.listByFiltrosPaginado(filtros, pageable);
        return ResponseEntity.ok(new ResponseData<>(vehiculos));
    }
}
