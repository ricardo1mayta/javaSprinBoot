package pe.com.avivel.sistemas.guiaselectronicas.restapi.controller.query.security;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.security.RolQueryDTO;
import pe.com.avivel.sistemas.guiaselectronicas.entities.security.RolTipo;
import pe.com.avivel.sistemas.guiaselectronicas.entities.util.EstadoGeneral;
import pe.com.avivel.sistemas.guiaselectronicas.restapi.response.ResponseData;
import pe.com.avivel.sistemas.guiaselectronicas.service.spec.security.RolQueryService;

import java.util.List;
import java.util.Optional;

import static pe.com.avivel.sistemas.guiaselectronicas.restapi.auth.UrlConfig.RolUrl.TAG;
import static pe.com.avivel.sistemas.guiaselectronicas.restapi.auth.UrlConfig.RolUrl.Query.*;
import static pe.com.avivel.sistemas.guiaselectronicas.restapi.controller.ConstansResponse.*;

@Slf4j
@Tag(name = TAG)
@Validated
@RequestMapping(value = PREFIX, produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@RequiredArgsConstructor
public class RolQueryController {

    private final RolQueryService rolQueryService;

    @PreAuthorize("hasAnyRole('_ADMIN')")
    @Operation(summary = "Obtiene un rol por id", tags = TAG)
    @GetMapping(value = FIND_BY_ID)
    public ResponseEntity<?> findById(@PathVariable(name = "id") Integer id) {
        Optional<RolQueryDTO> rolOp = rolQueryService.findById(id);

        if(rolOp.isEmpty()){
            return new ResponseEntity<>(new ResponseData<>(NO_DATA, ""), HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(new ResponseData<>(rolOp.get()));
    }

    @PreAuthorize("hasAnyRole('_ADMIN')")
    @Operation(summary = "Listado de roles activos por tipo (M:Menu, P:Permiso)", tags = TAG)
    @GetMapping(value = LIST_BY_ROL_TIPO)
    public ResponseEntity<?> listByRolTipo(@PathVariable(name = "tipo") RolTipo tipo) {
        List<RolQueryDTO> result = rolQueryService.listByRolTipo(tipo);
        return ResponseEntity.ok(new ResponseData<>(result));
    }

    @PreAuthorize("hasAnyRole('_ADMIN')")
    @Operation(summary = "Obtiene un listado de los roles activos de un usuario especificado", tags = TAG)
    @GetMapping(value = LIST_BY_USUARIO_ID)
    public ResponseEntity<?> listByUsuarioId(@PathVariable(name = "usuario-id") Integer usuarioId) {
        List<RolQueryDTO> result = rolQueryService.listByUsuarioId(usuarioId);
        return ResponseEntity.ok(new ResponseData<>(result));
    }

    @PreAuthorize("hasAnyRole('_ADMIN')")
    @Operation(summary = "Obtiene un listado de todos los roles de manera paginada", tags = TAG)
    @GetMapping(value = LIST_ALL_BY_FILTROS_PAGINADO)
    public ResponseEntity<?> listAllByFiltrosPaginado(
            @RequestParam(value = "nombre", required = false, defaultValue = "") String nombre,
            @RequestParam(value = "estado", required = false, defaultValue = "T") EstadoGeneral estado,
            @ParameterObject Pageable pageable
    ) {
        Page<RolQueryDTO> result = rolQueryService.listAllByFiltrosPaginado(nombre, estado, pageable);
        return ResponseEntity.ok(new ResponseData<>(result));
    }

}
