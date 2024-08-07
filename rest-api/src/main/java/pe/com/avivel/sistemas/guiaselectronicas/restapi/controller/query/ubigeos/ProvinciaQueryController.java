package pe.com.avivel.sistemas.guiaselectronicas.restapi.controller.query.ubigeos;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.ubigeos.ProvinciaQueryDTO;
import pe.com.avivel.sistemas.guiaselectronicas.restapi.response.ResponseData;
import pe.com.avivel.sistemas.guiaselectronicas.service.spec.ubigeos.ProvinciaQueryService;

import java.util.List;

import static pe.com.avivel.sistemas.guiaselectronicas.restapi.auth.UrlConfig.ProvinciaUrl.Query.*;
import static pe.com.avivel.sistemas.guiaselectronicas.restapi.auth.UrlConfig.ProvinciaUrl.TAG;

@Slf4j
@Tag(name = TAG)
@RequestMapping(value = PREFIX, produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@RequiredArgsConstructor
public class ProvinciaQueryController {

    private final ProvinciaQueryService provinciaQueryService;

    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Obtiene un listado de las provincias activas que se encuentran en el departamento indicado", tags = TAG)
    @GetMapping(value = LIST_BY_DEPARTAMENTO_ID)
    public ResponseEntity<?> listByDepartamentoId(@PathVariable("departamento-id") Integer departamentoId) {
        List<ProvinciaQueryDTO> provincias = provinciaQueryService.listByDepartamentoId(departamentoId);
        return ResponseEntity.ok(new ResponseData<>(provincias));
    }
}
