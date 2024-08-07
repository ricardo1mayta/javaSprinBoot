package pe.com.avivel.sistemas.guiaselectronicas.restapi.controller.command.guia_remision.guia_remision_remitente;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.command.guia_remision.guia_remision_remitente.GuiaRemisionRemitenteCommandDTO;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.guia_remision.guia_remision_remitente.GuiaRemisionRemitenteDetalladoQueryDTO;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.guia_remision.guia_remision_remitente.ResultEmisionQueryDTO;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.guia_remision.guia_remision_remitente.ResultObtenerCdrQueryDTO;
import pe.com.avivel.sistemas.guiaselectronicas.entities.auxiliares.guia_remision.EstadosGuia;
import pe.com.avivel.sistemas.guiaselectronicas.restapi.response.ResponseData;
import pe.com.avivel.sistemas.guiaselectronicas.service.spec.guia_remision.guia_remision_remitente.GuiaRemisionRemitenteCommandService;

import javax.mail.MessagingException;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import static pe.com.avivel.sistemas.guiaselectronicas.restapi.auth.UrlConfig.GuiaRemisionRemitenteUrl.TAG;
import static pe.com.avivel.sistemas.guiaselectronicas.restapi.auth.UrlConfig.GuiaRemisionRemitenteUrl.Command.*;
import static pe.com.avivel.sistemas.guiaselectronicas.restapi.controller.ConstansResponse.*;

@Slf4j
@Tag(name = TAG)
@Validated
@RequestMapping(value = PREFIX, produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@RequiredArgsConstructor
public class GuiaRemisionRemitenteCommandController {

    private final GuiaRemisionRemitenteCommandService guiaRemisionRemitenteCommandService;

    @Operation(summary = "Crear una guia remision remitente", tags = TAG)
    @PreAuthorize("hasAnyRole('_ADMIN', '_ALMACEN_JEFATURA', '_ALMACEN', '_OPERARIO')")
    @PostMapping(value = CREATE)
    public ResponseEntity<?> create(@Validated(Default.class) @RequestBody GuiaRemisionRemitenteCommandDTO dto) {
        GuiaRemisionRemitenteDetalladoQueryDTO guiaRemisionRemitente = guiaRemisionRemitenteCommandService.create(dto);
        return new ResponseEntity<>(new ResponseData<>(guiaRemisionRemitente, REGISTRO_CREADO, ""), HttpStatus.CREATED);
    }

    @Operation(summary = "Actualizar una guia remision remitente", tags = TAG)
    @PreAuthorize("hasAnyRole('_ADMIN', '_ALMACEN_JEFATURA', '_ALMACEN', '_OPERARIO')")
    @PutMapping(value = UPDATE)
    public ResponseEntity<?> update(@PathVariable("id") Integer id, @Validated(Default.class) @RequestBody GuiaRemisionRemitenteCommandDTO dto) {
        GuiaRemisionRemitenteDetalladoQueryDTO guiaRemisionRemitente = guiaRemisionRemitenteCommandService.update(id, dto);
        return new ResponseEntity<>(new ResponseData<>(guiaRemisionRemitente, REGISTRO_ACTUALIZADO, ""), HttpStatus.CREATED);
    }

    @Operation(summary = "Anular una guia remision remitente", tags = TAG)
    @PreAuthorize("hasAnyRole('_ADMIN', '_CONTABILIDAD')")
    @GetMapping(value = ANULAR)
    public ResponseEntity<?> anular(@PathVariable("id") Integer id) {
        guiaRemisionRemitenteCommandService.anular(id);
        return new ResponseEntity<>(new ResponseData<>(REGISTRO_ACTUALIZADO, ""), HttpStatus.CREATED);
    }

    @Operation(summary = "Emitir una guia remision remitente", tags = TAG)
    @PreAuthorize("hasAnyRole('_ADMIN', '_ALMACEN_JEFATURA', '_ALMACEN', '_OPERARIO')")
    @GetMapping(value = EMITIR)
    public ResponseEntity<?> emitir(@PathVariable("id") Integer id) {
        ResultEmisionQueryDTO result = guiaRemisionRemitenteCommandService.emitir(id);
        return new ResponseEntity<>(new ResponseData<>(result.getEstadoId(), result.getMensaje(), ""),
                result.getEstadoId().equals(EstadosGuia.ACEPTADO.getId()) ? HttpStatus.CREATED : HttpStatus.CONFLICT);
    }

    @Operation(summary = "Obtener CDR de una guia de remision remitente ya enviado a EFACT", tags = TAG)
    @PreAuthorize("hasAnyRole('_ADMIN', '_ALMACEN_JEFATURA', '_ALMACEN', '_OPERARIO')")
    @PostMapping(value = OBTENER_CDR)
    public ResponseEntity<?> obtenerCdr(@PathVariable("id") Integer id) throws IOException {
        ResultObtenerCdrQueryDTO result = guiaRemisionRemitenteCommandService.obtenerCdr(id);

        if(result.getResource() == null){
            return new ResponseEntity<>(new ResponseData<>(REGISTRO_NO_ACTUALIZADO, result.getMensaje()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        Resource recurso = result.getResource();

        return ResponseEntity.ok()
                .contentLength(recurso.contentLength())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .cacheControl(CacheControl.noCache())
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        ContentDisposition.attachment()
                                .filename(recurso.getFilename())
                                .build().toString()
                )
                .body("");
    }

    @Operation(summary = "Obtener XML de una guia de remision remitente ya aceptada en EFACT", tags = TAG)
    @PreAuthorize("hasAnyRole('_ADMIN', '_ALMACEN_JEFATURA', '_ALMACEN', '_OPERARIO')")
    @PostMapping(value = OBTENER_XML)
    public ResponseEntity<?> obtenerXml(@PathVariable("id") Integer id) throws IOException {
        Resource recurso = guiaRemisionRemitenteCommandService.obtenerXml(id);

        return ResponseEntity.ok()
                .contentLength(recurso.contentLength())
                .contentType(MediaType.APPLICATION_XML)
                .cacheControl(CacheControl.noCache())
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        ContentDisposition.attachment()
                                .filename(recurso.getFilename())
                                .build().toString()
                )
                .body(recurso);
    }

    @Operation(summary = "Obtener PDF de una guia de remision remitente ya aceptada en EFACT", tags = TAG)
    @PreAuthorize("hasAnyRole('_ADMIN', '_ALMACEN_JEFATURA', '_ALMACEN', '_OPERARIO')")
    @GetMapping(value = OBTENER_PDF)
    public ResponseEntity<?> obtenerPdf(@PathVariable("id") Integer id) throws IOException {
        Resource recurso = guiaRemisionRemitenteCommandService.obtenerPdf(id);

        return ResponseEntity.ok()
                .contentLength(recurso.contentLength())
                .contentType(MediaType.APPLICATION_PDF)
                .cacheControl(CacheControl.noCache())
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        ContentDisposition.attachment()
                                .filename(recurso.getFilename())
                                .build().toString()
                )
                .body(recurso);
    }
    @Operation(summary = "Obtener PDF de una guia de remision remitente ya aceptada en EFACT", tags = TAG)
    @PreAuthorize("hasAnyRole('_ADMIN', '_ALMACEN_JEFATURA', '_ALMACEN', '_OPERARIO')")
    @GetMapping(value = OBTENER_PDF_NUEVO)
    public ResponseEntity<?> obtenerPdfNuevo(@PathVariable("id") Integer id) throws Exception {
        Resource recurso = guiaRemisionRemitenteCommandService.obtenerPdfNuevo(id);

        return ResponseEntity.ok()
                .contentLength(recurso.contentLength())
                .contentType(MediaType.APPLICATION_PDF)
                .cacheControl(CacheControl.noCache())
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        ContentDisposition.attachment()
                                .filename(recurso.getFilename())
                                .build().toString()
                )
                .body(recurso);
    }
    @Operation(summary = "Obtener PDFs de una guia de remision remitente ya aceptada en EFACT", tags = TAG)
    @PreAuthorize("hasAnyRole('_ADMIN', '_ALMACEN_JEFATURA', '_ALMACEN', '_OPERARIO')")
    @PostMapping(value = OBTENER_PDF_MASIVO)
    public ResponseEntity<?> obtenerPdfMasivo(@Valid @RequestBody @NotEmpty List<@NotNull Integer> ids) throws IOException {
        Resource recurso = guiaRemisionRemitenteCommandService.obtenerPdfMasivo(ids);

        return ResponseEntity.ok()
                .contentLength(recurso.contentLength())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .cacheControl(CacheControl.noCache())
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        ContentDisposition.attachment()
                                .filename(recurso.getFilename())
                                .build().toString()
                )
                .body(recurso);
    }

    @Operation(summary = "Enviar correo para una guia de remision remitente ya aceptada en EFACT", tags = TAG)
    @PreAuthorize("hasAnyRole('_ADMIN', '_ALMACEN_JEFATURA', '_ALMACEN', '_OPERARIO')")
    @PostMapping(value = ENVIAR_CORREO)
    public ResponseEntity<?> enviarCorreo(@PathVariable("id") Integer id,
                                          @Valid @RequestBody @NotEmpty List<@NotBlank @Email String> correos) throws MessagingException, UnsupportedEncodingException {

        guiaRemisionRemitenteCommandService.enviarCorreo(id, correos.toArray(String[]::new));
        return new ResponseEntity<>(new ResponseData<>(ENVIO_EXITOSO, ""), HttpStatus.CREATED);
    }
}
