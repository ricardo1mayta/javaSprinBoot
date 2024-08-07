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
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.ubigeos.DistritoQueryDTO;
import pe.com.avivel.sistemas.guiaselectronicas.restapi.response.ResponseData;
import pe.com.avivel.sistemas.guiaselectronicas.service.spec.ubigeos.DistritoQueryService;

import java.util.List;

import static pe.com.avivel.sistemas.guiaselectronicas.restapi.auth.UrlConfig.DistritoUrl.Query.*;
import static pe.com.avivel.sistemas.guiaselectronicas.restapi.auth.UrlConfig.DistritoUrl.TAG;

@Slf4j
@Tag(name = TAG)
@RequestMapping(value = PREFIX, produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@RequiredArgsConstructor
public class DistritoQueryController {

    private final DistritoQueryService distritoQueryService;

    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Obtiene un listado de los distritos activos que se encuentran en la provincia indicada", tags = TAG)
    @GetMapping(value = LIST_BY_PROVINCIA_ID)
    public ResponseEntity<?> listByProvinciaId(@PathVariable("provincia-id") Integer provinciaId) {
        List<DistritoQueryDTO> distritos = distritoQueryService.listByProvinciaId(provinciaId);
        return ResponseEntity.ok(new ResponseData<>(distritos));
    }
}
