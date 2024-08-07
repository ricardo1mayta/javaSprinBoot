package pe.com.avivel.sistemas.guiaselectronicas.service.spec.guia_remision.guia_remision_remitente;

import pe.com.avivel.sistemas.guiaselectronicas.commons.files.FileNameAwareByteArrayResource;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.command.guia_remision.guia_remision_remitente.GuiaRemisionRemitenteCommandDTO;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.guia_remision.guia_remision_remitente.*;

import javax.mail.MessagingException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.List;

public interface GuiaRemisionRemitenteCommandService {

    GuiaRemisionRemitenteDetalladoQueryDTO create(GuiaRemisionRemitenteCommandDTO dto);

    GuiaRemisionRemitenteDetalladoQueryDTO update(Integer id, GuiaRemisionRemitenteCommandDTO dto);

    void anular(Integer id);

    ResultEmisionQueryDTO emitir(Integer id);

    ResultObtenerCdrQueryDTO obtenerCdr(Integer id);

    FileNameAwareByteArrayResource obtenerXml(Integer id);

    FileNameAwareByteArrayResource obtenerPdf(Integer id);
    FileNameAwareByteArrayResource obtenerPdfNuevo(Integer id) throws Exception;

    FileNameAwareByteArrayResource obtenerPdfMasivo(List<Integer> ids);

    void enviarCorreo(Integer id, String[] correos) throws MessagingException, UnsupportedEncodingException;
}
