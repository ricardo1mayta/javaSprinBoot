package pe.com.avivel.sistemas.guiaselectronicas.restapi.controller.command.security;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.command.guia_remision.seccion.SeccionTransportistaCommandDTO;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.command.security.PasswordUpdateDTO;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.command.security.UsuarioCreateDTO;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.command.security.UsuarioUpdateDTO;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.security.UsuarioArticuloQueryDTO;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.security.UsuarioEntidadQueryDTO;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.security.UsuarioQueryDTO;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.security.UsuarioSerieQueryDTO;
import pe.com.avivel.sistemas.guiaselectronicas.restapi.response.ResponseData;
import pe.com.avivel.sistemas.guiaselectronicas.service.spec.security.UsuarioCommandService;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import static pe.com.avivel.sistemas.guiaselectronicas.restapi.auth.UrlConfig.UsuarioUrl.TAG;
import static pe.com.avivel.sistemas.guiaselectronicas.restapi.auth.UrlConfig.UsuarioUrl.Command.*;
import static pe.com.avivel.sistemas.guiaselectronicas.restapi.controller.ConstansResponse.*;

@Slf4j
@Tag(name = TAG)
@Validated
@RequestMapping(value = PREFIX, produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@RequiredArgsConstructor
public class UsuarioCommandPrivateController {

    private final UsuarioCommandService usuarioCommandService;

    @PreAuthorize("hasAnyRole('_ADMIN')")
    @Operation(summary = "Crear un usuario", tags = TAG)
    @PostMapping(value = CREATE)
    public ResponseEntity<?> create(@Valid @RequestBody UsuarioCreateDTO usuarioCreateDTO){
        UsuarioQueryDTO usuario = usuarioCommandService.create(usuarioCreateDTO);
        return new ResponseEntity<>(new ResponseData<>(usuario, REGISTRO_CREADO, ""), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('_ADMIN')")
    @Operation(summary = "Actualizar un usuario", tags = TAG)
    @PutMapping(value = UPDATE)
    public ResponseEntity<?> update(@PathVariable("id") Integer id, @Valid @RequestBody UsuarioUpdateDTO usuarioUpdateDTO) {
        UsuarioQueryDTO usuario = usuarioCommandService.update(id, usuarioUpdateDTO);
        return new ResponseEntity<>(new ResponseData<>(usuario, REGISTRO_ACTUALIZADO, ""), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('_ADMIN')")
    @Operation(summary = "Actualizar un rol a un usuario", tags = TAG)
    @PostMapping(value = ADD_USUARIO_ROL)
    public ResponseEntity<?> agregarRolUsuario(@PathVariable("usuario-id") Integer usuarioId,
                                               @PathVariable("rol-id") Integer rolId) {
        usuarioCommandService.addUsuarioRol(usuarioId, rolId);
        return new ResponseEntity<>(new ResponseData<>(REGISTRO_CREADO, ""), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('_ADMIN')")
    @Operation(summary = "Eliminar un rol asociado a un usuario", tags = TAG)
    @DeleteMapping(value = DELETE_USUARIO_ROL)
    public ResponseEntity<?> eliminarRolUsuario(@PathVariable("usuario-id") Integer usuarioId,
                                                @PathVariable("rol-id") Integer rolId) {
        usuarioCommandService.deleteUsuarioRol(usuarioId, rolId);
        return new ResponseEntity<>(new ResponseData<>(REGISTRO_ACTUALIZADO, ""), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('_ADMIN')")
    @Operation(summary = "Establecer el password de un usuario", tags = TAG)
    @PutMapping(value = SET_PASSWORD)
    public ResponseEntity<?> setPassword(@PathVariable("id") Integer id, @Valid @NotBlank @RequestBody String password){
        int result = usuarioCommandService.setPassword(id, password);
        if(result > 0){
            return new ResponseEntity<>(new ResponseData<>(REGISTRO_ACTUALIZADO, ""), HttpStatus.CREATED);
        }

        return new ResponseEntity<>(new ResponseData<>(REGISTRO_NO_ACTUALIZADO, ""), HttpStatus.BAD_REQUEST);
    }

    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Actualizar el password de un usuario", tags = TAG)
    @PutMapping(value = UPDATE_PASSWORD)
    public ResponseEntity<?> updatePassword(@Valid @RequestBody PasswordUpdateDTO passwordUpdateDTO) {
        int passwordUpdated = usuarioCommandService.updatePassword(
                passwordUpdateDTO.getOldPassword(), passwordUpdateDTO.getNewPassword());
        return new ResponseEntity<>(new ResponseData<>(passwordUpdated, REGISTRO_ACTUALIZADO, ""), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('_ADMIN')")
    @Operation(summary = "Activar o desactivar un usuario", tags = TAG)
    @PutMapping(value = UPDATE_ESTADO)
    public ResponseEntity<?> updateEstado(@PathVariable("id") Integer id,
                                            @Valid @RequestBody @NotNull boolean activado) {
        int result = usuarioCommandService.updateEstadoById(id, activado);
        if(result > 0){
            return new ResponseEntity<>(new ResponseData<>(REGISTRO_ACTUALIZADO, ""), HttpStatus.CREATED);
        }

        return new ResponseEntity<>(new ResponseData<>(REGISTRO_NO_ACTUALIZADO, ""), HttpStatus.BAD_REQUEST);
    }

    @PreAuthorize("hasAnyRole('_ADMIN')")
    @Operation(summary = "Agregar una serie a un usuario", tags = TAG)
    @PostMapping(value = ADD_USUARIO_SERIE)
    public ResponseEntity<?> addUsuarioSerie(
            @Positive @PathVariable("usuario-id") Integer usuarioId,
            @Positive @PathVariable("serie-id") Integer serieId ) {
        UsuarioSerieQueryDTO usuarioSerie = usuarioCommandService.addUsuarioSerie(usuarioId, serieId);
        return new ResponseEntity<>(new ResponseData<>(usuarioSerie,REGISTRO_CREADO, ""), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('_ADMIN')")
    @Operation(summary = "Eliminar una serie asociada a un usuario", tags = TAG)
    @DeleteMapping(value = DELETE_USUARIO_SERIE)
    public ResponseEntity<?> deleteUsuarioSerie(@Positive @PathVariable("usuario-serie-id") Integer usuarioSerieId) {
        usuarioCommandService.deleteUsuarioSerie(usuarioSerieId);
        return new ResponseEntity<>(new ResponseData<>(REGISTRO_ELIMINADO, ""), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('_ADMIN')")
    @Operation(summary = "Agregar un articulo a un usuario", tags = TAG)
    @PostMapping(value = ADD_USUARIO_ARTICULO)
    public ResponseEntity<?> addUsuarioArticulo(
            @Positive @PathVariable("usuario-id") Integer usuarioId,
            @Positive @PathVariable("articulo-id") Integer articuloId ) {
        UsuarioArticuloQueryDTO usuarioArticulo = usuarioCommandService.addUsuarioArticulo(usuarioId, articuloId);
        return new ResponseEntity<>(new ResponseData<>(usuarioArticulo,REGISTRO_CREADO, ""), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('_ADMIN')")
    @Operation(summary = "Eliminar una articulo asociado a un usuario", tags = TAG)
    @DeleteMapping(value = DELETE_USUARIO_ARTICULO)
    public ResponseEntity<?> deleteUsuarioArticulo(@Positive @PathVariable("usuario-articulo-id") Integer usuarioArticuloId) {
        usuarioCommandService.deleteUsuarioArticulo(usuarioArticuloId);
        return new ResponseEntity<>(new ResponseData<>(REGISTRO_ELIMINADO, ""), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('_ADMIN')")
    @Operation(summary = "Agregar una entidad a un usuario", tags = TAG)
    @PostMapping(value = ADD_USUARIO_ENTIDAD)
    public ResponseEntity<?> addUsuarioEntidad(
            @Positive @PathVariable("usuario-id") Integer usuarioId,
            @Positive @PathVariable("entidad-id") Integer entidadId ) {
        UsuarioEntidadQueryDTO usuarioEntidad = usuarioCommandService.addUsuarioEntidad(usuarioId, entidadId);
        return new ResponseEntity<>(new ResponseData<>(usuarioEntidad, REGISTRO_CREADO, ""), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('_ADMIN')")
    @Operation(summary = "Eliminar una entidad asociada a un usuario", tags = TAG)
    @DeleteMapping(value = DELETE_USUARIO_ENTIDAD)
    public ResponseEntity<?> deleteUsuarioEntidad(@Positive @PathVariable("usuario-entidad-id") Integer usuarioEntidadId) {
        usuarioCommandService.deleteUsuarioEntidad(usuarioEntidadId);
        return new ResponseEntity<>(new ResponseData<>(REGISTRO_ELIMINADO, ""), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('_ADMIN')")
    @Operation(summary = "Actualizar datos de transportista de un usuario externo", tags = TAG)
    @PutMapping(value = UPDATE_DATOS_TRANSPORTISTA)
    public ResponseEntity<?> updateDatosTransportista(@PathVariable("usuario-id") Integer usuarioId ,@Valid @RequestBody SeccionTransportistaCommandDTO transportistaCommandDTO) {
        usuarioCommandService.updateDatosTransportista(usuarioId, transportistaCommandDTO);
        return new ResponseEntity<>(new ResponseData<>(REGISTRO_ACTUALIZADO, ""), HttpStatus.CREATED);
    }
}
