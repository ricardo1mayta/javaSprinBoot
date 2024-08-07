package pe.com.avivel.sistemas.guiaselectronicas.restapi.controller.command.security;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pe.com.avivel.sistemas.guiaselectronicas.restapi.response.ResponseData;
import pe.com.avivel.sistemas.guiaselectronicas.service.spec.security.RolCommandService;

import java.util.List;

import static pe.com.avivel.sistemas.guiaselectronicas.restapi.auth.UrlConfig.RolUrl.TAG;
import static pe.com.avivel.sistemas.guiaselectronicas.restapi.auth.UrlConfig.RolUrl.Command.*;
import static pe.com.avivel.sistemas.guiaselectronicas.restapi.controller.ConstansResponse.*;

@Slf4j
@Tag(name = TAG)
@RequestMapping(value = PREFIX, produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@RequiredArgsConstructor
public class RolCommandController {

    private final RolCommandService rolCommandService;

    @PreAuthorize("hasAnyRole('_ADMIN')")
    @Operation(summary = "Agregar menus a un rol", tags = TAG)
    @PostMapping(value = CREATE_ROL_MENUS)
    public ResponseEntity<?> createRolMenus(@PathVariable("rol-id") Integer rolId,
                                            @Validated @RequestBody List<Integer> menuIds){
        rolCommandService.createRolMenus(rolId, menuIds);
        return new ResponseEntity<>(new ResponseData<>(REGISTRO_CREADO, ""), HttpStatus.CREATED);
    }

}
