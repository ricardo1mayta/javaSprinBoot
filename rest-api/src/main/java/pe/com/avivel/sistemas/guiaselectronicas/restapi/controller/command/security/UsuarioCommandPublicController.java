package pe.com.avivel.sistemas.guiaselectronicas.restapi.controller.command.security;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.command.security.LoginDTO;
import pe.com.avivel.sistemas.guiaselectronicas.restapi.response.ResponseData;
import pe.com.avivel.sistemas.guiaselectronicas.service.spec.security.JwtService;

import javax.validation.Valid;

import static pe.com.avivel.sistemas.guiaselectronicas.restapi.auth.UrlConfig.UsuarioUrl.TAG;
import static pe.com.avivel.sistemas.guiaselectronicas.restapi.auth.UrlConfig.UsuarioUrl.CommandPublic.*;

@Slf4j
@Tag(name = TAG)
@RequestMapping(value = PREFIX, produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@RequiredArgsConstructor
public class UsuarioCommandPublicController {

    private final JwtService jwtService;

    private static final String LOGIN_SUCCESS = "Inicio de sesion exitoso.";
    private static final String LOGOUT_SUCCESS = "Fin de sesion exitoso.";

    @SecurityRequirements()
    @Operation(summary = "Iniciar sesión", description = "Endpoint para generar JWT", tags = TAG)
    @PostMapping(value = LOGIN)
    public ResponseEntity<?> login(@Valid @ParameterObject @ModelAttribute LoginDTO loginDTO) {
        String jwtResult = jwtService.login(loginDTO);
        return new ResponseEntity<>(new ResponseData<>(jwtResult, LOGIN_SUCCESS, ""), HttpStatus.CREATED);
    }

    @SecurityRequirements()
    @Operation(summary = "Cerrar sesión", description = "Endpoint para finalizar la sesión", tags = TAG)
    @PostMapping(value = LOGOUT)
    public ResponseEntity<?> logout(@RequestBody String token) {
        jwtService.logout(token);
        return new ResponseEntity<>(new ResponseData<>(LOGOUT_SUCCESS, ""), HttpStatus.CREATED);
    }

}
