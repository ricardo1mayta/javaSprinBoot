package pe.com.avivel.sistemas.guiaselectronicas.restapi.controller.query.test;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.com.avivel.sistemas.guiaselectronicas.restapi.response.ResponseData;

import static pe.com.avivel.sistemas.guiaselectronicas.restapi.auth.UrlConfig.TestingUrl.TAG;
import static pe.com.avivel.sistemas.guiaselectronicas.restapi.auth.UrlConfig.TestingUrl.Query.*;

@Slf4j
@RestController
@Tag(name = TAG)
@RequestMapping(value = PREFIX , produces = MediaType.APPLICATION_JSON_VALUE)
public class TestQueryController {

    @SecurityRequirements()
    @Operation(summary = "Prueba de la API", description = "Endpoint de prueba", tags = TAG)
    @GetMapping(value = PRUEBA)
    public ResponseEntity<?> prueba(){
        return ResponseEntity.ok(new ResponseData<>("Prueba"));
    }
}
