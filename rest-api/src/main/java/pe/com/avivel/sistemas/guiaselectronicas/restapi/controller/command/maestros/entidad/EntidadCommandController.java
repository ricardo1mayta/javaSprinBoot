package pe.com.avivel.sistemas.guiaselectronicas.restapi.controller.command.maestros.entidad;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.command.maestros.entidad.EntidadAlmacenCommandDTO;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.command.maestros.entidad.EntidadCommandDTO;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.command.maestros.entidad.EntidadCorreoCommandDTO;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.maestros.entidad.EntidadAlmacenQueryDTO;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.maestros.entidad.EntidadCorreoQueryDTO;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.maestros.entidad.EntidadQueryDTO;
import pe.com.avivel.sistemas.guiaselectronicas.restapi.response.ResponseData;
import pe.com.avivel.sistemas.guiaselectronicas.service.spec.maestros.entidad.EntidadCommandService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import static pe.com.avivel.sistemas.guiaselectronicas.restapi.auth.UrlConfig.EntidadUrl.TAG;
import static pe.com.avivel.sistemas.guiaselectronicas.restapi.auth.UrlConfig.EntidadUrl.Command.*;
import static pe.com.avivel.sistemas.guiaselectronicas.restapi.controller.ConstansResponse.*;

@Slf4j
@Tag(name = TAG)
@RequestMapping(value = PREFIX, produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@RequiredArgsConstructor
public class EntidadCommandController {

    private final EntidadCommandService entidadCommandService;

    @PreAuthorize("hasAnyRole('_ADMIN', '_ALMACEN_JEFATURA', '_ALMACEN')")
    @Operation(summary = "Crear una entidad", tags = TAG)
    @PostMapping(value = CREATE)
    public ResponseEntity<?> create(@Valid @RequestBody EntidadCommandDTO entidadCommandDTO){
        EntidadQueryDTO entidad = entidadCommandService.create(entidadCommandDTO);
        return new ResponseEntity<>(new ResponseData<>(entidad, REGISTRO_CREADO, ""), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('_ADMIN', '_ALMACEN_JEFATURA', '_ALMACEN')")
    @Operation(summary = "Actualizar una entidad", tags = TAG)
    @PutMapping(value = UPDATE)
    public ResponseEntity<?> update(@PathVariable("id") Integer id, @Valid @RequestBody EntidadCommandDTO entidadCommandDTO) {
        EntidadQueryDTO entidad = entidadCommandService.update(id, entidadCommandDTO);
        return new ResponseEntity<>(new ResponseData<>(entidad, REGISTRO_ACTUALIZADO, ""), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('_ADMIN', '_ALMACEN_JEFATURA', '_ALMACEN')")
    @Operation(summary = "Activar o desactivar una entidad", tags = TAG)
    @PutMapping(value = UPDATE_ESTADO)
    public ResponseEntity<?> updateEstado(@PathVariable("id") Integer id,
                                          @Valid @RequestBody @NotNull boolean activado) {
        int result = entidadCommandService.updateEstadoById(id, activado);
        if(result > 0){
            return new ResponseEntity<>(new ResponseData<>(REGISTRO_ACTUALIZADO, ""), HttpStatus.CREATED);
        }

        return new ResponseEntity<>(new ResponseData<>(REGISTRO_NO_ACTUALIZADO, ""), HttpStatus.BAD_REQUEST);
    }

    //Entidad Almacen
    @PreAuthorize("hasAnyRole('_ADMIN', '_ALMACEN_JEFATURA', '_ALMACEN')")
    @Operation(summary = "Crear un almacen de una entidad", tags = TAG)
    @PostMapping(value = CREATE_ALMACEN)
    public ResponseEntity<?> createAlmacen(@Valid @RequestBody EntidadAlmacenCommandDTO entidadAlmacenCommandDTO){
        EntidadAlmacenQueryDTO entidadAlmacen = entidadCommandService.createAlmacen(entidadAlmacenCommandDTO);
        return new ResponseEntity<>(new ResponseData<>(entidadAlmacen, REGISTRO_CREADO, ""), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('_ADMIN', '_ALMACEN_JEFATURA', '_ALMACEN')")
    @Operation(summary = "Actualizar un almacen de una entidad", tags = TAG)
    @PutMapping(value = UPDATE_ALMACEN)
    public ResponseEntity<?> updateAlmacen(@PathVariable("id") Integer id, @Valid @RequestBody EntidadAlmacenCommandDTO entidadAlmacenCommandDTO) {
        EntidadAlmacenQueryDTO entidadAlmacen = entidadCommandService.updateAlmacen(id, entidadAlmacenCommandDTO);
        return new ResponseEntity<>(new ResponseData<>(entidadAlmacen, REGISTRO_ACTUALIZADO, ""), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('_ADMIN', '_ALMACEN_JEFATURA', '_ALMACEN')")
    @Operation(summary = "Activar o desactivar el almacen de una entidad", tags = TAG)
    @PutMapping(value = UPDATE_ESTADO_ALMACEN)
    public ResponseEntity<?> updateEstadoAlmacen(@PathVariable("id") Integer id,
                                                 @Valid @RequestBody @NotNull boolean activado) {
        int result = entidadCommandService.updateEstadoAlmacenById(id, activado);
        if(result > 0){
            return new ResponseEntity<>(new ResponseData<>(REGISTRO_ACTUALIZADO, ""), HttpStatus.CREATED);
        }

        return new ResponseEntity<>(new ResponseData<>(REGISTRO_NO_ACTUALIZADO, ""), HttpStatus.BAD_REQUEST);
    }

    //Entidad Correo
    @PreAuthorize("hasAnyRole('_ADMIN', '_ALMACEN_JEFATURA', '_ALMACEN')")
    @Operation(summary = "Registrar un correo de una entidad", tags = TAG)
    @PostMapping(value = CREATE_CORREO)
    public ResponseEntity<?> createCorreo(@Valid @RequestBody EntidadCorreoCommandDTO entidadCorreoCommandDTO){
        EntidadCorreoQueryDTO entidadCorreo = entidadCommandService.createCorreo(entidadCorreoCommandDTO);
        return new ResponseEntity<>(new ResponseData<>(entidadCorreo, REGISTRO_CREADO, ""), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('_ADMIN', '_ALMACEN_JEFATURA', '_ALMACEN')")
    @Operation(summary = "Actualizar un correo de una entidad", tags = TAG)
    @PutMapping(value = UPDATE_CORREO)
    public ResponseEntity<?> updateCorreo(@PathVariable("id") Integer id, @Valid @RequestBody EntidadCorreoCommandDTO entidadCorreoCommandDTO) {
        EntidadCorreoQueryDTO entidadCorreo = entidadCommandService.updateCorreo(id, entidadCorreoCommandDTO);
        return new ResponseEntity<>(new ResponseData<>(entidadCorreo, REGISTRO_ACTUALIZADO, ""), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('_ADMIN', '_ALMACEN_JEFATURA', '_ALMACEN')")
    @Operation(summary = "Activar o desactivar el correo de una entidad", tags = TAG)
    @PutMapping(value = UPDATE_ESTADO_CORREO)
    public ResponseEntity<?> updateEstadoCorreo(@PathVariable("id") Integer id,
                                                @Valid @RequestBody @NotNull boolean activado) {
        int result = entidadCommandService.updateEstadoCorreoById(id, activado);
        if(result > 0){
            return new ResponseEntity<>(new ResponseData<>(REGISTRO_ACTUALIZADO, ""), HttpStatus.CREATED);
        }

        return new ResponseEntity<>(new ResponseData<>(REGISTRO_NO_ACTUALIZADO, ""), HttpStatus.BAD_REQUEST);
    }
}
