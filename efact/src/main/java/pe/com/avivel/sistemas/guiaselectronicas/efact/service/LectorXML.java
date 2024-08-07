package pe.com.avivel.sistemas.guiaselectronicas.efact.service;

import lombok.extern.slf4j.Slf4j;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;
import pe.com.avivel.sistemas.guiaselectronicas.commons.files.FileRoutes;
import pe.com.avivel.sistemas.guiaselectronicas.efact.exceptions.EfactException;
import pe.com.avivel.sistemas.guiaselectronicas.entities.guia_remision.guia_remision_remitente.GuiaRemisionRemitente;

import java.io.File;


@Slf4j
public class LectorXML {


    public static void leerCDR(GuiaRemisionRemitente guia) throws EfactException {

        SAXBuilder builder = new SAXBuilder();
        File xmlFile;
        try {
            Document document;
            Element rootNode;

            xmlFile = new File(FileRoutes.CDR_XML_FOLDER+ guia.getNombreGuiaElectronica()+".xml");

            // Se crea el documento a traves del archivo
            document = (Document) builder.build(xmlFile);

            // Se obtiene el nodo raíz
            rootNode = document.getRootElement();

            // DocumentResponse
            Element documentResponse = rootNode.getChild("DocumentResponse", Namespace.getNamespace("urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2"));

            /*
            Respuesta
             */
            // Response
            Element response = documentResponse.getChild("Response", Namespace.getNamespace("urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2"));
            // Description
            Element description = response.getChild("Description", Namespace.getNamespace("urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2"));
            String descriptionTxt = description.getTextNormalize();

            /*
            Digest Value
             */
            // DocumentReference

            Element documentReference = documentResponse.getChild("DocumentReference", Namespace.getNamespace("urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2"));
            // Attachment
            try
            {
                Element attachment = documentReference.getChild("Attachment", Namespace.getNamespace("urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2"));
                // ExternalReference
                Element externalReference = attachment.getChild("ExternalReference", Namespace.getNamespace("urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2"));
                // DocumentHash
                Element documentHash = externalReference.getChild("DocumentHash", Namespace.getNamespace("urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2"));
                String documentHashTxt = documentHash.getTextNormalize();

                guia.setEfactObservacion(descriptionTxt);
                guia.setEfactDigestValue(documentHashTxt);

            } catch (Exception e){
                log.info("El CDR no tiene observaciones.");
            }


            log.info("El CDR del comprobante {} fue leído satisfactoriamente.", guia.toString());
            //LOGGER.log(Level.INFO, "El CDR del comprobante {0} fue leído satisfactoriamente.", documento.toString());
        } catch (JDOMException ex) {
            ex.printStackTrace();
            throw new EfactException("Ha ocurrido un error al leer el archivo " + guia.getNombreGuiaElectronica() + ".xml" + ".", ex);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new EfactException("Ha ocurrido un error inesperado.", ex);
        }
    }

}
