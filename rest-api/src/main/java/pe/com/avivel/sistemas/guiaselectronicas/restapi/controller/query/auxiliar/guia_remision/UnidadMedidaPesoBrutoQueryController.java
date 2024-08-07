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
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.auxiliar.guia_remision.UnidadMedidaPesoBrutoQueryDTO;
import pe.com.avivel.sistemas.guiaselectronicas.restapi.response.ResponseData;
import pe.com.avivel.sistemas.guiaselectronicas.service.spec.auxiliares.guia_remision.UnidadMedidaPesoBrutoQueryService;

import java.util.List;

import static pe.com.avivel.sistemas.guiaselectronicas.restapi.auth.UrlConfig.UnidadMedidaPesoBrutoUrl.TAG;
import static pe.com.avivel.sistemas.guiaselectronicas.restapi.auth.UrlConfig.UnidadMedidaPesoBrutoUrl.Query.*;

@Slf4j
@Tag(name = TAG)
@RequestMapping(value = PREFIX, produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@RequiredArgsConstructor
public class UnidadMedidaPesoBrutoQueryController {

    private final UnidadMedidaPesoBrutoQueryService unidadMedidaPesoBrutoQueryService;

    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Obtiene un listado de las unidades de medidas de peso bruto activas", tags = TAG)
    @GetMapping(value = LIST)
    public ResponseEntity<?> list() {
        List<UnidadMedidaPesoBrutoQueryDTO> unidades = unidadMedidaPesoBrutoQueryService.list();
        return ResponseEntity.ok(new ResponseData<>(unidades));
    }
}
