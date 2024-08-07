package pe.com.avivel.sistemas.guiaselectronicas.restapi.controller.query.auxiliar.guia_remision;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.auxiliar.guia_remision.UnidadMedidaQueryDTO;
import pe.com.avivel.sistemas.guiaselectronicas.restapi.response.ResponseData;
import pe.com.avivel.sistemas.guiaselectronicas.service.spec.auxiliares.guia_remision.UnidadMedidaQueryService;

import java.util.List;

import static pe.com.avivel.sistemas.guiaselectronicas.restapi.auth.UrlConfig.UnidadMedidaUrl.TAG;
import static pe.com.avivel.sistemas.guiaselectronicas.restapi.auth.UrlConfig.UnidadMedidaUrl.Query.*;

@Slf4j
@Tag(name = TAG)
@RequestMapping(value = PREFIX, produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@RequiredArgsConstructor
public class UnidadMedidaQueryController {

    private final UnidadMedidaQueryService unidadMedidaQueryService;

    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Obtiene un listado de las unidades de medidas activas", tags = TAG)
    @GetMapping(value = LIST)
    public ResponseEntity<?> list() {
        List<UnidadMedidaQueryDTO> unidades = unidadMedidaQueryService.list();
        return ResponseEntity.ok(new ResponseData<>(unidades));
    }
}
