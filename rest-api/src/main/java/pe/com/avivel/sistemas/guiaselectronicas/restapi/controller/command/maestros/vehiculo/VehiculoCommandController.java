package pe.com.avivel.sistemas.guiaselectronicas.restapi.controller.command.maestros.vehiculo;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.command.maestros.vehiculo.VehiculoCommandDTO;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.maestros.vehiculo.VehiculoQueryDTO;
import pe.com.avivel.sistemas.guiaselectronicas.restapi.response.ResponseData;
import pe.com.avivel.sistemas.guiaselectronicas.service.spec.maestros.vehiculo.VehiculoCommandService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import static pe.com.avivel.sistemas.guiaselectronicas.restapi.auth.UrlConfig.VehiculoUrl.TAG;
import static pe.com.avivel.sistemas.guiaselectronicas.restapi.auth.UrlConfig.VehiculoUrl.Command.*;
import static pe.com.avivel.sistemas.guiaselectronicas.restapi.controller.ConstansResponse.*;

@Slf4j
@Tag(name = TAG)
@RequestMapping(value = PREFIX, produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@RequiredArgsConstructor
public class VehiculoCommandController {

    private final VehiculoCommandService vehiculoCommandService;

    @PreAuthorize("hasAnyRole('_ADMIN', '_ALMACEN_JEFATURA', '_ALMACEN')")
    @Operation(summary = "Crear un vehiculo", tags = TAG)
    @PostMapping(value = CREATE)
    public ResponseEntity<?> create(@Valid @RequestBody VehiculoCommandDTO vehiculoCommandDTO){
        VehiculoQueryDTO vehiculo = vehiculoCommandService.create(vehiculoCommandDTO);
        return new ResponseEntity<>(new ResponseData<>(vehiculo, REGISTRO_CREADO, ""), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('_ADMIN', '_ALMACEN_JEFATURA', '_ALMACEN')")
    @Operation(summary = "Actualizar un vehiculo", tags = TAG)
    @PutMapping(value = UPDATE)
    public ResponseEntity<?> update(@PathVariable("id") Integer id, @Valid @RequestBody VehiculoCommandDTO vehiculoCommandDTO) {
        VehiculoQueryDTO vehiculo = vehiculoCommandService.update(id, vehiculoCommandDTO);
        return new ResponseEntity<>(new ResponseData<>(vehiculo, REGISTRO_ACTUALIZADO, ""), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('_ADMIN', '_ALMACEN_JEFATURA', '_ALMACEN')")
    @Operation(summary = "Activar o desactivar un vehiculo", tags = TAG)
    @PutMapping(value = UPDATE_ESTADO)
    public ResponseEntity<?> updateEstado(@PathVariable("id") Integer id,
                                          @Valid @RequestBody @NotNull boolean activado) {
        int result = vehiculoCommandService.updateEstadoById(id, activado);
        if(result > 0){
            return new ResponseEntity<>(new ResponseData<>(REGISTRO_ACTUALIZADO, ""), HttpStatus.CREATED);
        }

        return new ResponseEntity<>(new ResponseData<>(REGISTRO_NO_ACTUALIZADO, ""), HttpStatus.BAD_REQUEST);
    }
}
