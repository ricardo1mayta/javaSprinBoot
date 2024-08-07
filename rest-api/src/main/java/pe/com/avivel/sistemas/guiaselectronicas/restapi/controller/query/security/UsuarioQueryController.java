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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.guia_remision.seccion.TransportistaQueryDTO;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.maestros.SerieQueryDTO;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.security.UsuarioArticuloQueryDTO;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.security.UsuarioEntidadQueryDTO;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.security.UsuarioQueryDTO;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.security.UsuarioSerieQueryDTO;
import pe.com.avivel.sistemas.guiaselectronicas.entities.util.EstadoGeneral;
import pe.com.avivel.sistemas.guiaselectronicas.restapi.response.ResponseData;
import pe.com.avivel.sistemas.guiaselectronicas.service.spec.security.UsuarioQueryService;

import java.util.List;
import java.util.Optional;

import static pe.com.avivel.sistemas.guiaselectronicas.restapi.auth.UrlConfig.UsuarioUrl.TAG;
import static pe.com.avivel.sistemas.guiaselectronicas.restapi.auth.UrlConfig.UsuarioUrl.Query.*;
import static pe.com.avivel.sistemas.guiaselectronicas.restapi.controller.ConstansResponse.*;
import static pe.com.avivel.sistemas.guiaselectronicas.entities.util.EstadoGeneral.Fields.*;

@Slf4j
@Tag(name = TAG)
@RequestMapping(value = PREFIX, produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@RequiredArgsConstructor
public class UsuarioQueryController {

    private final UsuarioQueryService usuarioQueryService;

    @PreAuthorize("hasAnyRole('_ADMIN')")
    @Operation(summary = "Obtiene un usuario por id", tags = TAG)
    @GetMapping(value = FIND_BY_ID)
    public ResponseEntity<?> findById(@PathVariable(name = "id") Integer id) {
        Optional<UsuarioQueryDTO> usuarioOp = usuarioQueryService.findById(id);

        if(usuarioOp.isEmpty()){
            return new ResponseEntity<>(new ResponseData<>(NO_DATA, ""), HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(new ResponseData<>(usuarioOp.get()));
    }

    @PreAuthorize("hasAnyRole('_ADMIN')")
    @Operation(summary = "Obtiene un usuario por username", tags = TAG)
    @GetMapping(value = FIND_BY_USERNAME)
    public ResponseEntity<?> findByUsername(@PathVariable(name = "username") String username) {
        Optional<UsuarioQueryDTO> usuarioOp = usuarioQueryService.findByUsername(username);

        if(usuarioOp.isEmpty()){
            return new ResponseEntity<>(new ResponseData<>(NO_DATA, ""), HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(new ResponseData<>(usuarioOp.get()));
    }

    @PreAuthorize("hasAnyRole('_ADMIN') or authentication?.datosSesion?.usuarioExterno == true")
    @Operation(summary = "Obtiene los datos de transportista asociado al usuario actual", tags = TAG)
    @GetMapping(value = FIND_DATOS_TRANSPORTISTA)
    public ResponseEntity<?> findDatosTransportista() {
        Optional<TransportistaQueryDTO> transportistaOp = usuarioQueryService.findDatosTransportista();

        if(transportistaOp.isEmpty()){
            return new ResponseEntity<>(new ResponseData<>(NO_DATA, ""), HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(new ResponseData<>(transportistaOp.get()));
    }

    @PreAuthorize("hasAnyRole('_ADMIN')")
    @Operation(summary = "Obtiene un listado de los usuarios de manera paginada.", tags = TAG)
    @GetMapping(value = LIST_ALL_BY_FILTROS_PAGINADO)
    public ResponseEntity<?> listAllByFiltrosPaginados(
            @RequestParam(name = "username", required = false, defaultValue = "") String username,
            @RequestParam(name = "estado", required = false, defaultValue = T) EstadoGeneral estado,
            @PageableDefault(sort = "id", direction = Sort.Direction.DESC) @ParameterObject Pageable pageable
    ) {
        Page<UsuarioQueryDTO> usuarios = usuarioQueryService.listAllByFiltrosPaginado(username, estado, pageable);
        return ResponseEntity.ok(new ResponseData<>(usuarios));
    }


    @PreAuthorize("hasAnyRole('_ADMIN', '_ALMACEN_JEFATURA', '_ALMACEN', '_OPERARIO')")
    @Operation(summary = "Obtiene un listado de las series disponibles al usuario actual", tags = TAG)
    @GetMapping(value = LIST_SERIES_DISPONIBLES)
    public ResponseEntity<?> listSeriesDisponibles() {
        List<SerieQueryDTO> series = usuarioQueryService.listSeriesDisponibles();
        return ResponseEntity.ok(new ResponseData<>(series));
    }

    @PreAuthorize("hasAnyRole('_ADMIN')")
    @Operation(summary = "Obtiene un listado de las series asignadas al usuario actual", tags = TAG)
    @GetMapping(value = LIST_ALL_SERIES_ASIGNADAS)
    public ResponseEntity<?> listAllSeriesAsignadas(@PathVariable("usuario-id") Integer usuarioId) {
        List<UsuarioSerieQueryDTO> series = usuarioQueryService.listAllSeriesAsignadas(usuarioId);
        return ResponseEntity.ok(new ResponseData<>(series));
    }

    @PreAuthorize("hasAnyRole('_ADMIN')")
    @Operation(summary = "Obtiene un listado de los articulos asignados al usuario actual", tags = TAG)
    @GetMapping(value = LIST_ALL_ARTICULOS_ASIGNADOS)
    public ResponseEntity<?> listAllArticulosAsignados(@PathVariable("usuario-id") Integer usuarioId) {
        List<UsuarioArticuloQueryDTO> series = usuarioQueryService.listAllArticulosAsignados(usuarioId);
        return ResponseEntity.ok(new ResponseData<>(series));
    }

    @PreAuthorize("hasAnyRole('_ADMIN')")
    @Operation(summary = "Obtiene un listado de las entidades asignadas al usuario actual", tags = TAG)
    @GetMapping(value = LIST_ALL_ENTIDADES_ASIGNADAS)
    public ResponseEntity<?> listAllEntidadesAsignadas(@PathVariable("usuario-id") Integer usuarioId) {
        List<UsuarioEntidadQueryDTO> series = usuarioQueryService.listAllEntidadesAsignadas(usuarioId);
        return ResponseEntity.ok(new ResponseData<>(series));
    }
}
