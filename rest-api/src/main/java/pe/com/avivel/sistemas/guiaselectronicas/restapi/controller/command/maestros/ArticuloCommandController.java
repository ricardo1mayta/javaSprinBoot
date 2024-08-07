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
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.command.maestros.ArticuloCommandDTO;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.maestros.ArticuloQueryDTO;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.maestros.ArticuloUnidadMedidaQueryDTO;
import pe.com.avivel.sistemas.guiaselectronicas.restapi.response.ResponseData;
import pe.com.avivel.sistemas.guiaselectronicas.service.spec.maestros.ArticuloCommandService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import static pe.com.avivel.sistemas.guiaselectronicas.restapi.auth.UrlConfig.ArticuloUrl.TAG;
import static pe.com.avivel.sistemas.guiaselectronicas.restapi.auth.UrlConfig.ArticuloUrl.Command.*;
import static pe.com.avivel.sistemas.guiaselectronicas.restapi.controller.ConstansResponse.*;

@Slf4j
@Tag(name = TAG)
@RequestMapping(value = PREFIX, produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@RequiredArgsConstructor
public class ArticuloCommandController {

    private final ArticuloCommandService articuloCommandService;

    @PreAuthorize("hasAnyRole('_ADMIN', '_ALMACEN_JEFATURA', '_ALMACEN')")
    @Operation(summary = "Crear un articulo", tags = TAG)
    @PostMapping(value = CREATE)
    public ResponseEntity<?> create(@Valid @RequestBody ArticuloCommandDTO articuloCommandDTO){
        ArticuloQueryDTO articulo = articuloCommandService.create(articuloCommandDTO);
        return new ResponseEntity<>(new ResponseData<>(articulo, REGISTRO_CREADO, ""), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('_ADMIN', '_ALMACEN_JEFATURA', '_ALMACEN')")
    @Operation(summary = "Actualizar un articulo", tags = TAG)
    @PutMapping(value = UPDATE)
    public ResponseEntity<?> update(@PathVariable("id") Integer id, @Valid @RequestBody ArticuloCommandDTO articuloCommandDTO) {
        ArticuloQueryDTO articulo = articuloCommandService.update(id, articuloCommandDTO);
        return new ResponseEntity<>(new ResponseData<>(articulo, REGISTRO_ACTUALIZADO, ""), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('_ADMIN', '_ALMACEN_JEFATURA', '_ALMACEN')")
    @Operation(summary = "Activar o desactivar un articulo", tags = TAG)
    @PutMapping(value = UPDATE_ESTADO)
    public ResponseEntity<?> updateEstado(@PathVariable("id") Integer id,
                                          @Valid @RequestBody @NotNull boolean activado) {
        int result = articuloCommandService.updateEstadoById(id, activado);
        if(result > 0){
            return new ResponseEntity<>(new ResponseData<>(REGISTRO_ACTUALIZADO, ""), HttpStatus.CREATED);
        }

        return new ResponseEntity<>(new ResponseData<>(REGISTRO_NO_ACTUALIZADO, ""), HttpStatus.BAD_REQUEST);
    }

    @PreAuthorize("hasAnyRole('_ADMIN', '_ALMACEN_JEFATURA', '_ALMACEN')")
    @Operation(summary = "Crear una unidad medida asignada a un articulo", tags = TAG)
    @PostMapping(value = CREATE_UNIDAD_MEDIDA)
    public ResponseEntity<?> createUnidadMedida(@PathVariable("articulo-id") Integer articuloId,
                                                @PathVariable("unidad-medida-id") Integer unidadMedidaId){
        ArticuloUnidadMedidaQueryDTO articuloUnidadMedida = articuloCommandService.createUnidadMedida(articuloId, unidadMedidaId);
        return new ResponseEntity<>(new ResponseData<>(articuloUnidadMedida, REGISTRO_CREADO, ""), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('_ADMIN', '_ALMACEN_JEFATURA', '_ALMACEN')")
    @Operation(summary = "Eliminar una unidad medida asignada a un articulo", tags = TAG)
    @DeleteMapping(value = DELETE_UNIDAD_MEDIDA)
    public ResponseEntity<?> deleteUnidadMedida(
            @PathVariable("articulo-unidad-medida-id") Integer articuloUnidadMedidaId){
        articuloCommandService.deleteUnidadMedida(articuloUnidadMedidaId);
        return new ResponseEntity<>(new ResponseData<>(REGISTRO_ELIMINADO, ""), HttpStatus.CREATED);
    }
}
