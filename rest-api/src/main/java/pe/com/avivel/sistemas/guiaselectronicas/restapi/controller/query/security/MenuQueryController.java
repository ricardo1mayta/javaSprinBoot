package pe.com.avivel.sistemas.guiaselectronicas.restapi.controller.query.security;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.security.MenuQueryDTO;
import pe.com.avivel.sistemas.guiaselectronicas.restapi.response.ResponseData;
import pe.com.avivel.sistemas.guiaselectronicas.service.spec.security.MenuQueryService;

import java.util.List;
import java.util.Optional;

import static pe.com.avivel.sistemas.guiaselectronicas.restapi.auth.UrlConfig.MenuUrl.TAG;
import static pe.com.avivel.sistemas.guiaselectronicas.restapi.auth.UrlConfig.MenuUrl.Query.*;
import static pe.com.avivel.sistemas.guiaselectronicas.restapi.controller.ConstansResponse.*;

@Slf4j
@Tag(name = TAG)
@Validated
@RequestMapping(value = PREFIX, produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@RequiredArgsConstructor
public class MenuQueryController {

    private final MenuQueryService menuQueryService;

    @PreAuthorize("hasAnyRole('_ADMIN')")
    @Operation(summary = "Obtiene un menu por id", tags = TAG)
    @GetMapping(value = FIND_BY_ID)
    public ResponseEntity<?> findById(@PathVariable(name = "id") Integer id) {
        Optional<MenuQueryDTO> menuOp = menuQueryService.findById(id);

        if(menuOp.isEmpty()){
            return new ResponseEntity<>(new ResponseData<>(NO_DATA, ""), HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(new ResponseData<>(menuOp.get()));
    }

    @PreAuthorize("hasAnyRole('_ADMIN')")
    @Operation(summary = "Listado de menus activos", tags = TAG)
    @GetMapping(value = LIST)
    public ResponseEntity<?> list() {
        List<MenuQueryDTO> result = menuQueryService.list();
        return ResponseEntity.ok(new ResponseData<>(result));
    }

    @PreAuthorize("hasAnyRole('_ADMIN')")
    @Operation(summary = "Listado de menus activos por usuario", tags = TAG)
    @GetMapping(value = LIST_BY_USUARIO_ID)
    public ResponseEntity<?> listByUsuarioId(@PathVariable("usuario-id") Integer usuarioId) {
        List<MenuQueryDTO> result = menuQueryService.listByUsuarioId(usuarioId);
        return ResponseEntity.ok(new ResponseData<>(result));
    }

    @PreAuthorize("hasAnyRole('_ADMIN')")
    @Operation(summary = "Listado de menus activos sin jerarquias por usuario", tags = TAG)
    @GetMapping(value = LIST_BY_USUARIO_ID_SIN_JERARQUIA)
    public ResponseEntity<?> listByUsuarioIdSinJerarquias(@PathVariable("usuario-id") Integer usuarioId) {
        List<MenuQueryDTO> result = menuQueryService.listByUsuarioIdSinJerarquias(usuarioId);
        return ResponseEntity.ok(new ResponseData<>(result));
    }

    @PreAuthorize("hasAnyRole('_ADMIN')")
    @Operation(summary = "Listado de menus activos por rol", tags = TAG)
    @GetMapping(value = LIST_BY_ROL_ID)
    public ResponseEntity<?> listByRolId(@PathVariable("rol-id") Integer rolId) {
        List<MenuQueryDTO> result = menuQueryService.listByRolId(rolId);
        return ResponseEntity.ok(new ResponseData<>(result));
    }

    @PreAuthorize("hasAnyRole('_ADMIN')")
    @Operation(summary = "Listado de menus activos sin jerarquias por rol", tags = TAG)
    @GetMapping(value = LIST_BY_ROL_ID_SIN_JERARQUIA)
    public ResponseEntity<?> listByRolIdSinJerarquias(@PathVariable("rol-id") Integer rolId) {
        List<MenuQueryDTO> result = menuQueryService.listByRolIdSinJerarquias(rolId);
        return ResponseEntity.ok(new ResponseData<>(result));
    }
}
