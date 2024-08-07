package pe.com.avivel.sistemas.guiaselectronicas.efact.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;
import pe.com.avivel.sistemas.guiaselectronicas.commons.constans.GuiasConstans;
import pe.com.avivel.sistemas.guiaselectronicas.commons.efact.ResultEnvioGuiaOSE;
import pe.com.avivel.sistemas.guiaselectronicas.commons.efact.ResultObtenerCDR;
import pe.com.avivel.sistemas.guiaselectronicas.commons.files.FileRoutes;
import pe.com.avivel.sistemas.guiaselectronicas.efact.commons.EfactProperties;
import pe.com.avivel.sistemas.guiaselectronicas.efact.exceptions.EfactException;
import pe.com.avivel.sistemas.guiaselectronicas.efact.service.GuiasElectronicasEfact;
import pe.com.avivel.sistemas.guiaselectronicas.efact.service.LectorXML;
import pe.com.avivel.sistemas.guiaselectronicas.entities.guia_remision.guia_remision_remitente.GuiaRemisionRemitente;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import static pe.com.avivel.sistemas.guiaselectronicas.commons.constans.GuiasConstans.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class GuiasElectronicasEfactImpl implements GuiasElectronicasEfact {


    private final EfactProperties efactProperties;


    @Override
    public String obtenerTokenAutorizacion() throws EfactException {

        var urlApi = efactProperties.getGuiasEfactBaseUrlApi()+"oauth/token";
        CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpPost httpPost = new HttpPost(urlApi);
        String json = null;

        try{

            String credentials = Base64.getEncoder().encodeToString("client:secret".getBytes("UTF-8"));
            httpPost.setHeader("Authorization", "Basic " + credentials);
            httpPost.setHeader("Content-Type","application/x-www-form-urlencoded");

            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("grant_type","password"));
            params.add(new BasicNameValuePair("username", efactProperties.getGuiasEfactPlatformUsername()));
            params.add(new BasicNameValuePair("password", efactProperties.getGuiasEfactPlatformPassword()));
            httpPost.setEntity(new UrlEncodedFormEntity(params));

            json = httpClient.execute(httpPost, new StringResponseHandler());

            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNodeToker = mapper.readTree(json);
            var accessToken = rootNodeToker.path("access_token").asText();
            log.info("Token generado: {}", accessToken);

            return accessToken;

        }catch (RuntimeException | IOException ex){
            throw  new EfactException("Error al obtener el token: "+ ex);
        }

    }

    @Override
    public ResultEnvioGuiaOSE enviarGuiaRemision(String nombreGuia) throws EfactException {
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            String ticket = null;
            var result = new ResultEnvioGuiaOSE();

            String accessToken = this.obtenerTokenAutorizacion();

            log.info("Pasando token para enviar guia: {}", accessToken);
            if(accessToken!=null){

                var post = new HttpPost(efactProperties.getGuiasEfactBaseUrlApi()+"v1/document");
                post.setHeader("Authorization", "Bearer " + accessToken);
                //post.setHeader("Content-Type","application/x-www-form-urlencoded");
                MultipartEntityBuilder builder = MultipartEntityBuilder.create();
                builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
                byte[] xml = Files.readAllBytes(Paths.get(FileRoutes.SIGNED_XML_FOLDER + nombreGuia));

                log.info("tamaño del arquivo {}", xml.length);
                builder.addBinaryBody("file",xml, ContentType.create("text/xml"),nombreGuia);
                var entity = builder.build();
                post.setEntity(entity);

                var response = httpClient.execute(post);

                int status = response.getStatusLine().getStatusCode();

                log.info("STATUS CODE: {}",status);

                HttpEntity entityResponse = response.getEntity();
                String jsonResponse = (entityResponse!=null ? EntityUtils.toString(entityResponse) : null);

                if(jsonResponse!=null){

                    var objectMapper = new ObjectMapper();
                    JsonNode rootNodeResponse = objectMapper.readTree(jsonResponse);

                    if(status == STATUS_200){

                        ticket = rootNodeResponse.path("description").asText();
                        log.info("TICKET: {}", ticket);
                        result.setTicket(ticket);

                        result.setMessage("Guia enviada correctamente");
                        result.setStatus(String.valueOf(status));
                        log.info("ok_aqui______________------------------------------------");

                    }else {
                        String error= rootNodeResponse.path("description").asText();
                        result.setTicket(null);
                        result.setMessage(error);
                        result.setStatus(String.valueOf(status));
                    }
                }
            }

            return result;

        } catch (IOException e) {
            e.printStackTrace();
            throw new EfactException("Error al enviar el documento xml a Efact.",e);
        }
    }

    @Override
    public String enviarGuiaRemision(Integer guiaRemisionId) throws EfactException {
        return null;
    }

    @Override
    public ResultObtenerCDR obtenerCDR(String ticket, GuiaRemisionRemitente guia) throws EfactException {
        ResultObtenerCDR resultObtenerCDR = new ResultObtenerCDR();

        try{
            CloseableHttpClient httpClient = HttpClients.createDefault();
            var httpGet = new HttpGet(efactProperties.getGuiasEfactBaseUrlApi()+"v1/cdr/"+ticket);
            String accessToken = this.obtenerTokenAutorizacion();
            log.info("Pasando token para obtener cdr: {}", accessToken);

            if(accessToken!=null){
                httpGet.setHeader("Authorization","Bearer "+accessToken);
                var response = httpClient.execute(httpGet);
                int status = response.getStatusLine().getStatusCode();
                log.info("STATUS CODE: {}", status);
                var entity = response.getEntity();
                switch (status){
                    case 200:

                        if(entity != null){
                            resultObtenerCDR.setByteCDRFile(entity.getContent().readAllBytes());
                            resultObtenerCDR.setGuiaSituacion(ESTADO_ENVIADO_ACEPTADO);
                            resultObtenerCDR.setStatusCode(200);
                            resultObtenerCDR.setObservacion("El CDR para el comprobante se obtuvo satisfactoriamente.");
                            resultObtenerCDR.setCdrObtenido(true);
                            //resultObtenerCDR.setDigestValue(LectorXML.leerCDR(guia));
                        }
                        break;
                    case 202:
                        resultObtenerCDR.setGuiaSituacion(ESTADO_ENVIADO_VALIDACION);
                        resultObtenerCDR.setStatusCode(202);
                        resultObtenerCDR.setObservacion("El documento se encuentra en proceso de validación");
                        break;
                    case 412:
                        //Se obtiene el resultado del response
                        String jsonResponse = (entity != null ? EntityUtils.toString(entity) : null);
                        // Se transforma el Json y se obtiene el ticket
                        ObjectMapper mapper = new ObjectMapper();
                        JsonNode rootNodeResponse = mapper.readTree(jsonResponse);
                        String error = rootNodeResponse.path("description").asText();
                        resultObtenerCDR.setStatusCode(412);
                        resultObtenerCDR.setGuiaSituacion(ESTADO_ERROR);
                        resultObtenerCDR.setObservacion(error);
                        break;
                    default:
                        resultObtenerCDR.setGuiaSituacion(ESTADO_DESCONOCIDO);
                        resultObtenerCDR.setObservacion("El ticket no existe");
                        break;
                }
            }

            return resultObtenerCDR;

        }catch (IOException | ParseException | EfactException ex){
            throw new EfactException("Error al obtener el CDR de Efact",ex);
        }
    }


    @Override
    public byte[] obtenerXML(String ticket) throws EfactException {
        var byteXMLFirmadoFile = new byte[0];
        try{
            CloseableHttpClient httpClient = HttpClients.createDefault();
            var httpGet = new HttpGet(efactProperties.getGuiasEfactBaseUrlApi()+"v1/xml/"+ticket);
            String accessToken = this.obtenerTokenAutorizacion();
            httpGet.setHeader("Authorization","Bearer " + accessToken);

            var response = httpClient.execute(httpGet);

            int status = response.getStatusLine().getStatusCode();

            log.info("STATUS CODE DESCARGAR GUIA {}",status);

            if(status == STATUS_200){
                var entity = response.getEntity();

                if(entity!=null){
                    byteXMLFirmadoFile=entity.getContent().readAllBytes();
                }
            }

        }catch (IOException ex){
            throw new EfactException("Error al obtener el xml firmado de Efact",ex);
        }

        return byteXMLFirmadoFile;
    }

    @Override
    public byte[] obtenerPDF(String ticket) throws EfactException {
        var bytePdfFile = new byte[0];

        try{
            CloseableHttpClient httpClient = HttpClients.createDefault();
            var httpGet = new HttpGet(efactProperties.getGuiasEfactBaseUrlApi()+"v1/pdf/"+ticket);
            String accessToken = this.obtenerTokenAutorizacion();
            httpGet.setHeader("Authorization","Bearer " + accessToken);

            var response = httpClient.execute(httpGet);
            int status = response.getStatusLine().getStatusCode();

            log.info("STATUS CODE DESCARGAR PDF {}", status);

            if(status==STATUS_200){
                var entity = response.getEntity();

                if(entity !=null){
                    bytePdfFile = entity.getContent().readAllBytes();
                }
            }
        }catch (IOException ex){
            throw new EfactException("Error al obtener el PDF de Efact",ex);
        }

        return bytePdfFile;
    }

    private static class StringResponseHandler implements ResponseHandler<String> {
        @Override
        public String handleResponse(HttpResponse httpResponse) throws ClientProtocolException, IOException {
            int status = httpResponse.getStatusLine().getStatusCode();
            if (status >= 200 && status < 300) {
                HttpEntity entity = httpResponse.getEntity();
                return entity != null ? EntityUtils.toString(entity) : null;
            } else {
                throw new ClientProtocolException("Unexpected response status: " + status);
            }
        }
    }
}
