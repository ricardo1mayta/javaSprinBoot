package pe.com.avivel.sistemas.guiaselectronicas.restapi.controller.command.guia_remision.guia_remision_remitente;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.integration.IntegrationProperties;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.command.guia_remision.jventas.GuiaRemisionBodyDTO;
import pe.com.avivel.sistemas.guiaselectronicas.restapi.auth.UrlConfig;
import pe.com.avivel.sistemas.guiaselectronicas.restapi.response.ResponseData;
import pe.com.avivel.sistemas.guiaselectronicas.service.spec.guia_remision.guia_remision_remitente.GuiaRemisionRemitenteJVentasCommandService;


import static pe.com.avivel.sistemas.guiaselectronicas.restapi.auth.UrlConfig.GuiaRemisionRemitenteJVentasUrl.Command.CREATE;
import static pe.com.avivel.sistemas.guiaselectronicas.restapi.auth.UrlConfig.GuiaRemisionRemitenteJVentasUrl.Command.PREFIX;
import static pe.com.avivel.sistemas.guiaselectronicas.restapi.auth.UrlConfig.GuiaRemisionRemitenteJVentasUrl.TAG;


@Slf4j
@Tag(name = TAG)
@RequestMapping(value = PREFIX, produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class GuiaRemisionRemitenteJVentasCommandController {

    private final GuiaRemisionRemitenteJVentasCommandService guiaRemisionService;

    public GuiaRemisionRemitenteJVentasCommandController(GuiaRemisionRemitenteJVentasCommandService guiaRemisionService) {
        this.guiaRemisionService = guiaRemisionService;
    }


    @Operation(summary = "Crear una guia remision remitente desde el JVentas", tags = UrlConfig.GuiaRemisionRemitenteUrl.TAG)
    @PreAuthorize("hasAnyRole('_ADMIN', '_ALMACEN_JEFATURA', '_ALMACEN', '_OPERARIO')")
    @PostMapping(value = CREATE)
    public ResponseEntity<?> create(@RequestBody GuiaRemisionBodyDTO body){

        try{
            var result= guiaRemisionService.create(body);
            return ResponseEntity.ok(new ResponseData<>(result,"Guia generada correctamente","Se ha generado la guia: "+body.getOrdenPedidoNumeroSerie()));

        }catch (Exception e){
            e.printStackTrace();
            return  ResponseEntity.ok(new ResponseData<>("Error al generar la Guía Electrónica"));
        }

    }
}
