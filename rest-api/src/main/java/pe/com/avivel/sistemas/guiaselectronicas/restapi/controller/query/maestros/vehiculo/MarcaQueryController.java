package pe.com.avivel.sistemas.guiaselectronicas.restapi.controller.query.maestros.vehiculo;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.maestros.vehiculo.MarcaQueryDTO;
import pe.com.avivel.sistemas.guiaselectronicas.restapi.auth.UrlConfig;
import pe.com.avivel.sistemas.guiaselectronicas.restapi.response.ResponseData;
import pe.com.avivel.sistemas.guiaselectronicas.service.spec.maestros.vehiculo.MarcaQueryService;

import java.util.List;

import static pe.com.avivel.sistemas.guiaselectronicas.restapi.auth.UrlConfig.MarcaUrl.Query.LIST_MARCAS;
import static pe.com.avivel.sistemas.guiaselectronicas.restapi.auth.UrlConfig.MarcaUrl.Query.*;
import static pe.com.avivel.sistemas.guiaselectronicas.restapi.auth.UrlConfig.MarcaUrl.TAG;

@Slf4j
@Tag(name = TAG)
@RequestMapping(value = PREFIX, produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@RequiredArgsConstructor
public class MarcaQueryController {

    private final MarcaQueryService marcaQueryService;

    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Obtiene un listado de las marcas", tags = UrlConfig.MotivoTrasladoUrl.TAG)
    @GetMapping(value = LIST_MARCAS)
    public ResponseEntity<?> list() {
        List<MarcaQueryDTO> marcas = marcaQueryService.list();
        return ResponseEntity.ok(new ResponseData<>(marcas));
    }
}
