package pe.com.avivel.sistemas.guiaselectronicas.restapi.controller.command.maestros;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.command.maestros.AlmacenCommandDTO;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.maestros.AlmacenQueryDTO;
import pe.com.avivel.sistemas.guiaselectronicas.restapi.response.ResponseData;
import pe.com.avivel.sistemas.guiaselectronicas.service.spec.maestros.AlmacenCommandService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import static pe.com.avivel.sistemas.guiaselectronicas.restapi.auth.UrlConfig.AlmacenUrl.TAG;
import static pe.com.avivel.sistemas.guiaselectronicas.restapi.auth.UrlConfig.AlmacenUrl.Command.*;
import static pe.com.avivel.sistemas.guiaselectronicas.restapi.controller.ConstansResponse.*;

@Slf4j
@Tag(name = TAG)
@RequestMapping(value = PREFIX, produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@RequiredArgsConstructor
public class AlmacenCommandController {

    private final AlmacenCommandService almacenCommandService;

    @PreAuthorize("hasAnyRole('_ADMIN', '_ALMACEN_JEFATURA')")
    @Operation(summary = "Crear un almacen", tags = TAG)
    @PostMapping(value = CREATE)
    public ResponseEntity<?> create(@Valid @RequestBody AlmacenCommandDTO almacenCommandDTO){
        AlmacenQueryDTO almacen = almacenCommandService.create(almacenCommandDTO);
        return new ResponseEntity<>(new ResponseData<>(almacen, REGISTRO_CREADO, ""), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('_ADMIN', '_ALMACEN_JEFATURA')")
    @Operation(summary = "Actualizar un almacen", tags = TAG)
    @PutMapping(value = UPDATE)
    public ResponseEntity<?> update(@PathVariable("id") Integer id, @Valid @RequestBody AlmacenCommandDTO almacenCommandDTO) {
        AlmacenQueryDTO almacen = almacenCommandService.update(id, almacenCommandDTO);
        return new ResponseEntity<>(new ResponseData<>(almacen, REGISTRO_ACTUALIZADO, ""), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('_ADMIN', '_ALMACEN_JEFATURA')")
    @Operation(summary = "Activar o desactivar un almacen", tags = TAG)
    @PutMapping(value = UPDATE_ESTADO)
    public ResponseEntity<?> updateEstado(@PathVariable("id") Integer id,
                                          @Valid @RequestBody @NotNull boolean activado) {
        int result = almacenCommandService.updateEstadoById(id, activado);
        if(result > 0){
            return new ResponseEntity<>(new ResponseData<>(REGISTRO_ACTUALIZADO, ""), HttpStatus.CREATED);
        }

        return new ResponseEntity<>(new ResponseData<>(REGISTRO_NO_ACTUALIZADO, ""), HttpStatus.BAD_REQUEST);
    }


}
