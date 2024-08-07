package pe.com.avivel.sistemas.guiaselectronicas.restapi.controller.query.maestros.vehiculo;


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
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.maestros.vehiculo.CategoriaQueryDTO;
import pe.com.avivel.sistemas.guiaselectronicas.restapi.auth.UrlConfig;
import pe.com.avivel.sistemas.guiaselectronicas.restapi.response.ResponseData;
import pe.com.avivel.sistemas.guiaselectronicas.service.spec.maestros.vehiculo.CategoriaQueryService;

import java.util.List;

import static pe.com.avivel.sistemas.guiaselectronicas.restapi.auth.UrlConfig.CategoriaUrl.Query.LIST_CATEGORIAS;
import static pe.com.avivel.sistemas.guiaselectronicas.restapi.auth.UrlConfig.CategoriaUrl.Query.*;
import static pe.com.avivel.sistemas.guiaselectronicas.restapi.auth.UrlConfig.CategoriaUrl.TAG;

@Slf4j
@Tag(name = TAG)
@RequestMapping(value = PREFIX, produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@RequiredArgsConstructor
public class CategoriaQueryController {

    private final CategoriaQueryService categoriaQueryService;

    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Obtiene un listado de las categorias de vehiculos", tags = UrlConfig.MotivoTrasladoUrl.TAG)
    @GetMapping(value = LIST_CATEGORIAS)
    public ResponseEntity<?> list() {
        List<CategoriaQueryDTO> marcas = categoriaQueryService.list();
        return ResponseEntity.ok(new ResponseData<>(marcas));
    }
}
