package pe.com.avivel.sistemas.guiaselectronicas.filesadapter.service.impl;

import org.springframework.stereotype.Service;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import pe.com.avivel.sistemas.guiaselectronicas.commons.constans.GuiasConstans;
import pe.com.avivel.sistemas.guiaselectronicas.commons.empresa.EmpresaProperties;
import pe.com.avivel.sistemas.guiaselectronicas.commons.fechas.Fecha;
import pe.com.avivel.sistemas.guiaselectronicas.commons.files.FileRoutes;
import pe.com.avivel.sistemas.guiaselectronicas.entities.guia_remision.guia_remision_remitente.GuiaRemisionRemitente;
import pe.com.avivel.sistemas.guiaselectronicas.entities.guia_remision.guia_remision_remitente.detalle.Conductor;
import pe.com.avivel.sistemas.guiaselectronicas.filesadapter.exceptions.XMLGuiaRemisionException;
import pe.com.avivel.sistemas.guiaselectronicas.filesadapter.service.XMLGuiaRemision;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

import static pe.com.avivel.sistemas.guiaselectronicas.commons.constans.GuiasConstans.CODIGO_PAIS_PERU;

@Service
public class XMLGuiaRemisionImpl implements XMLGuiaRemision {

    @Override
    public boolean generarXML(GuiaRemisionRemitente guiaRemision) throws XMLGuiaRemisionException {

        var pathFile = "";

        try{
            pathFile = FileRoutes.SIGNED_XML_FOLDER+ guiaRemision.getNombreGuiaElectronica()+".xml";
            //pathFile = FileRoutes.UNSIGNED_XML_FOLDER+ guiaRemision.getNombreGuiaElectronica()+".xml";

            var xmlFile = new File(pathFile);

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setNamespaceAware(true);
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.newDocument();

            //xmlns del documento xml
            Element invoice = doc.createElementNS("urn:oasis:names:specification:ubl:schema:xsd:DespatchAdvice-2", "DespatchAdvice");
            invoice.setAttribute("xmlns:cac", "urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2");
            invoice.setAttribute("xmlns:cbc", "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2");
            invoice.setAttribute("xmlns:ext", "urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2");
            doc.appendChild(invoice);


            //Version UBL del documento
            // cbc:UBLVersionID
            Element uBLVersionID = doc.createElement("cbc:UBLVersionID");
            invoice.appendChild(uBLVersionID);
            uBLVersionID.appendChild(doc.createTextNode("2.1"));

            //Version de estructura del documento
            // cbc:CustomizationID
            Element customizationID = doc.createElement("cbc:CustomizationID");
            invoice.appendChild(customizationID);
            customizationID.appendChild(doc.createTextNode("2.0"));


            //Serie y numero del documento
            // cbc:ID
            Element id = doc.createElement("cbc:ID");
            invoice.appendChild(id);
            id.appendChild(doc.createTextNode(guiaRemision.getNumeroSerie()));

            //Fecha de emisión
            // cbc:IssueDate
            Element issueDate = doc.createElement("cbc:IssueDate");
            invoice.appendChild(issueDate);
            issueDate.appendChild(doc.createTextNode(Fecha.toStringMySQL(guiaRemision.getFechaEmision())));

            //Hora de emisión
            //cbc:IssueTime
            Element dueDate = doc.createElement("cbc:IssueTime");
            invoice.appendChild(dueDate);
            dueDate.appendChild(doc.createTextNode(Fecha.toStringTimeFromMySQL(guiaRemision.getFechaEmision())));


            //Código del tipo de documento
            //cbc:DespatchAdviceTypeCode
            Element despatchAdviceTypeCode = doc.createElement("cbc:DespatchAdviceTypeCode");
            invoice.appendChild(despatchAdviceTypeCode);
            despatchAdviceTypeCode.appendChild(doc.createTextNode(GuiasConstans.CODIGO_GUIA_REMISON_REMITENTE));

            //Observaciones
            //cbc:Note
            Element note = doc.createElement("cbc:Note");
            invoice.appendChild(note);
            note.appendChild(doc.createTextNode(guiaRemision.getObservacion()));


            //Cantidad de items
            //cbc:LineCountNumeric
            Element lineCountNumeric = doc.createElement("cbc:LineCountNumeric");
            invoice.appendChild(lineCountNumeric);
            lineCountNumeric.appendChild(doc.createTextNode(String.valueOf(guiaRemision.getArticulos().size())));


            //Documentos relacionados
            if(guiaRemision.getDocumentoRelacionados()!=null){
                var listaDocumentos = guiaRemision.getDocumentoRelacionados();

                listaDocumentos.forEach(docRel->{
                    Element additionalDocumentReference=doc.createElement("cac:AdditionalDocumentReference");
                    //cac:AdditionalDocumentReference/cbc:ID
                    Element idAdditionalDocumentReference = doc.createElement("cbc:ID");
                    additionalDocumentReference.appendChild(idAdditionalDocumentReference);
                    idAdditionalDocumentReference.appendChild(doc.createTextNode(docRel.getNroDocumento()));
                    //cac:AdditionalDocumentReference/cbc:DocumentTypeCode
                    Element documentTypeCodeAdditionalDocumentReference = doc.createElement("cbc:DocumentTypeCode");
                    additionalDocumentReference.appendChild(documentTypeCodeAdditionalDocumentReference);
                    documentTypeCodeAdditionalDocumentReference.appendChild(doc.createTextNode(docRel.getTipoDocumento().getCodigoSunat()));

                    //cac:AdditionalDocumentReference/cac:IssuerParty
                    Element issuePartyAdditionalDocumentReference = doc.createElement("cac:IssuerParty");
                    additionalDocumentReference.appendChild(issuePartyAdditionalDocumentReference);

                    //cac:AdditionalDocumentReference/cac:IssuerParty/cac:PartyIdentification
                    Element partyIdentificationIssuePartyAdditionalDocumentReference = doc.createElement("cac:PartyIdentification");
                    issuePartyAdditionalDocumentReference.appendChild(partyIdentificationIssuePartyAdditionalDocumentReference);

                    //cac:AdditionalDocumentReference/cac:IssuerParty/cac:PartyIdentification/cbc:ID
                    Element idPartyIdentificationIssuePartyAdditionalDocumentReference = doc.createElement("cbc:ID");
                    idPartyIdentificationIssuePartyAdditionalDocumentReference.setAttribute("schemeID", "6");
                    idPartyIdentificationIssuePartyAdditionalDocumentReference.appendChild(doc.createTextNode(docRel.getRucEmisor()));
                    partyIdentificationIssuePartyAdditionalDocumentReference.appendChild(idPartyIdentificationIssuePartyAdditionalDocumentReference);

                    invoice.appendChild(additionalDocumentReference);
                });
            }


            //Firma del signatario
            //Solo se agrega el ruc del emisor
            //Y la razon social del emisor
            // cac:Signature
            Element signature = doc.createElement("cac:Signature");
            invoice.appendChild(signature);

            // cac:Signature/cbc:ID
            Element idSignature = doc.createElement("cbc:ID");
            signature.appendChild(idSignature);
            idSignature.appendChild(doc.createTextNode("IDSignature"));
            // cac:Signature/cac:SignatoryParty
            Element signatoryParty = doc.createElement("cac:SignatoryParty");
            signature.appendChild(signatoryParty);
            // cac:Signature/cac:SignatoryParty/cac:PartyIdentification
            Element partyIdentification = doc.createElement("cac:PartyIdentification");
            signatoryParty.appendChild(partyIdentification);
            // cac:Signature/cac:SignatoryParty/cac:PartyIdentification/cbc:ID
            Element idPartyIdentification = doc.createElement("cbc:ID");
            partyIdentification.appendChild(idPartyIdentification);
            idPartyIdentification.appendChild(doc.createTextNode(EmpresaProperties.getInstance().getRuc()));
            // cac:Signature/cac:SignatoryParty/cac:PartyName
            Element partyName = doc.createElement("cac:PartyName");
            signatoryParty.appendChild(partyName);
            // cac:Signature/cac:SignatoryParty/cac:PartyName/cbc:Name
            Element name = doc.createElement("cbc:Name");
            partyName.appendChild(name);
            name.appendChild(doc.createTextNode(EmpresaProperties.getInstance().getRasonSocial()));
            // cac:Signature/cac:DigitalSignatureAttachment
            Element digitalSignatureAttachment = doc.createElement("cac:DigitalSignatureAttachment");
            signature.appendChild(digitalSignatureAttachment);
            // cac:Signature/cac:DigitalSignatureAttachment/cac:ExternalReference
            Element externalReference = doc.createElement("cac:ExternalReference");
            digitalSignatureAttachment.appendChild(externalReference);
            // cac:Signature/cac:DigitalSignatureAttachment/cac:ExternalReference/cbc:URI
            Element uri = doc.createElement("cbc:URI");
            externalReference.appendChild(uri);
            uri.appendChild(doc.createTextNode("IDSignature"));



            //cac:DespatchSupplierParty
            Element despatchSupplierParty = doc.createElement("cac:DespatchSupplierParty");
            invoice.appendChild(despatchSupplierParty);

            //cac:DespatchSupplierParty/cac:Party
            Element despatchSupplierPartyParty= doc.createElement("cac:Party");
            despatchSupplierParty.appendChild(despatchSupplierPartyParty);

            //cac:DespatchSupplierParty/cac:Party/cac:PartyIdentification
            Element despatchSupplierPartyPartyPartyIdentification = doc.createElement("cac:PartyIdentification");
            despatchSupplierPartyParty.appendChild(despatchSupplierPartyPartyPartyIdentification);
            //cac:DespatchSupplierParty/cac:Party/cac:PartyIdentification/cbc:ID
            Element despatchSupplierPartyPartyPartyIdentificationID = doc.createElement("cbc:ID");
            despatchSupplierPartyPartyPartyIdentificationID.appendChild(doc.createTextNode(EmpresaProperties.getInstance().getRuc()));
            despatchSupplierPartyPartyPartyIdentificationID.setAttribute("schemeID","6");
            despatchSupplierPartyPartyPartyIdentification.appendChild(despatchSupplierPartyPartyPartyIdentificationID);

            //cac:DespatchSupplierParty/cac:Party/cac:PostalAddress
            Element despatchSupplierPartyPartyPostalAddress = doc.createElement("cac:PostalAddress");
            despatchSupplierPartyParty.appendChild(despatchSupplierPartyPartyPostalAddress);

            //cac:DespatchSupplierParty/cac:Party/cac:PostalAddress/cbc:ID
            Element despatchSupplierPartyPartyPostalAddressID = doc.createElement("cbc:ID");
            despatchSupplierPartyPartyPostalAddress.appendChild(despatchSupplierPartyPartyPostalAddressID);
            despatchSupplierPartyPartyPostalAddressID.appendChild(doc.createTextNode(EmpresaProperties.getInstance().getDistritoUbigeo()));

            Element despatchSupplierPartyPartyPostalAddressStreetName= doc.createElement("cbc:StreetName");
            despatchSupplierPartyPartyPostalAddress.appendChild(despatchSupplierPartyPartyPostalAddressStreetName);
            despatchSupplierPartyPartyPostalAddressStreetName.appendChild(doc.createTextNode(EmpresaProperties.getInstance().getDireccion()));



            Element despatchSupplierPartyPartyPostalAddressCityName = doc.createElement("cbc:CityName");
            despatchSupplierPartyPartyPostalAddress.appendChild(despatchSupplierPartyPartyPostalAddressCityName);
            despatchSupplierPartyPartyPostalAddressCityName.appendChild(doc.createTextNode(EmpresaProperties.getInstance().getDepartamento()));
            Element despatchSupplierPartyPartyPostalAddressCountrySubentity = doc.createElement("cbc:CountrySubentity");
            despatchSupplierPartyPartyPostalAddress.appendChild(despatchSupplierPartyPartyPostalAddressCountrySubentity);
            despatchSupplierPartyPartyPostalAddressCountrySubentity.appendChild(doc.createTextNode(EmpresaProperties.getInstance().getProvincia()));
            Element despatchSupplierPartyPartyPostalAddressDistrict = doc.createElement("cbc:District");
            despatchSupplierPartyPartyPostalAddress.appendChild(despatchSupplierPartyPartyPostalAddressDistrict);
            despatchSupplierPartyPartyPostalAddressDistrict.appendChild(doc.createTextNode(EmpresaProperties.getInstance().getDistrito()));


            Element despatchSupplierPartyPartyPostalAddressCountry = doc.createElement("cac:Country");
            despatchSupplierPartyPartyPostalAddress.appendChild(despatchSupplierPartyPartyPostalAddressCountry);
            Element despatchSupplierPartyPartyPostalAddressCountryIdentificationCode = doc.createElement("cbc:IdentificationCode");
            despatchSupplierPartyPartyPostalAddressCountry.appendChild(despatchSupplierPartyPartyPostalAddressCountryIdentificationCode);
            despatchSupplierPartyPartyPostalAddressCountryIdentificationCode.appendChild(doc.createTextNode(EmpresaProperties.getInstance().getCodigoPais()));


            ////cac:DespatchSupplierParty/cac:Party/cac:PartyLegalEntity
            Element despatchSupplierPartyPartyPartyLegalEntity = doc.createElement("cac:PartyLegalEntity");
            despatchSupplierPartyParty.appendChild(despatchSupplierPartyPartyPartyLegalEntity);
            Element despatchSupplierPartyPartyPartyLegalEntityRegistrationName = doc.createElement("cbc:RegistrationName");
            despatchSupplierPartyPartyPartyLegalEntity.appendChild(despatchSupplierPartyPartyPartyLegalEntityRegistrationName);
            despatchSupplierPartyPartyPartyLegalEntityRegistrationName.appendChild(doc.createTextNode(EmpresaProperties.getInstance().getRasonSocial()));

            //cac:DeliveryCustomerParty
            var deliveryCustomerParty = doc.createElement("cac:DeliveryCustomerParty");
            invoice.appendChild(deliveryCustomerParty);



            //cac:DeliveryCustomerParty/cac:Party
            Element deliveryCustomerPartyParty= doc.createElement("cac:Party");
            deliveryCustomerParty.appendChild(deliveryCustomerPartyParty);

            //cac:DeliveryCustomerParty/cac:Party/cac:PartyIdentification
            Element deliveryCustomerPartyPartyPartyIdentification = doc.createElement("cac:PartyIdentification");
            deliveryCustomerPartyParty.appendChild(deliveryCustomerPartyPartyPartyIdentification);

            //cac:DeliveryCustomerParty/cac:Party/cac:PartyIdentification/cbc:ID
            Element deliveryCustomerPartyPartyPartyIdentificationID = doc.createElement("cbc:ID");
            deliveryCustomerPartyPartyPartyIdentificationID.setAttribute("schemeID",guiaRemision.getDestinatario().getTipoDocumentoIdentidad().getCodigoSunat());
            deliveryCustomerPartyPartyPartyIdentificationID.appendChild(doc.createTextNode(guiaRemision.getDestinatario().getNroDocumentoIdentidad()));

            deliveryCustomerPartyPartyPartyIdentification.appendChild(deliveryCustomerPartyPartyPartyIdentificationID);



            //cac:DeliveryCustomerParty/cac:Party/cac:PostalAddress
            Element deliveryCustomerPartyPartyPostalAddress = doc.createElement("cac:PostalAddress");
            deliveryCustomerPartyParty.appendChild(deliveryCustomerPartyPartyPostalAddress);


            if(guiaRemision.getDestinatario().getDireccion()!=null){
                Element deliveryCustomerPartyPartyPostalAddressID = doc.createElement("cbc:ID");
                deliveryCustomerPartyPartyPostalAddress.appendChild(deliveryCustomerPartyPartyPostalAddressID);
                deliveryCustomerPartyPartyPostalAddressID.appendChild(doc.createTextNode(guiaRemision.getDestinatario().getDistrito().getCodigoUbigeo()));


                Element deliveryCustomerPartyPartyPostalAddressStreetName= doc.createElement("cbc:StreetName");
                deliveryCustomerPartyPartyPostalAddress.appendChild(deliveryCustomerPartyPartyPostalAddressStreetName);
                deliveryCustomerPartyPartyPostalAddressStreetName.appendChild(doc.createTextNode(guiaRemision.getDestinatario().getDireccion()));

                if(guiaRemision.getDestinatario().getDistrito().getProvincia().getDepartamento()!=null){
                    Element deliveryCustomerPartyPartyPostalAddressCityName = doc.createElement("cbc:CityName");
                    deliveryCustomerPartyPartyPostalAddress.appendChild(deliveryCustomerPartyPartyPostalAddressCityName);
                    deliveryCustomerPartyPartyPostalAddressCityName.appendChild(doc.createTextNode(guiaRemision.getDestinatario().getDistrito().getProvincia().getDepartamento().getDescripcion()));
                }

                if(guiaRemision.getDestinatario().getDistrito().getProvincia()!=null){
                    Element deliveryCustomerPartyPartyPostalAddressCountrySubentity = doc.createElement("cbc:CountrySubentity");
                    deliveryCustomerPartyPartyPostalAddress.appendChild(deliveryCustomerPartyPartyPostalAddressCountrySubentity);
                    deliveryCustomerPartyPartyPostalAddressCountrySubentity.appendChild(doc.createTextNode(guiaRemision.getDestinatario().getDistrito().getProvincia().getDescripcion()));
                }

                if(guiaRemision.getDestinatario().getDistrito()!=null){
                    Element deliveryCustomerPartyPartyPostalAddressDistrict = doc.createElement("cbc:District");
                    deliveryCustomerPartyPartyPostalAddress.appendChild(deliveryCustomerPartyPartyPostalAddressDistrict);
                    deliveryCustomerPartyPartyPostalAddressDistrict.appendChild(doc.createTextNode(guiaRemision.getDestinatario().getDistrito().getDescripcion()));
                }

                //cac:DeliveryCustomerParty/cac:Party/cac:PostalAddress/cac:Country
                Element deliveryCustomerPartyPartyPostalAddressCountry = doc.createElement("cac:Country");
                deliveryCustomerPartyPartyPostalAddress.appendChild(deliveryCustomerPartyPartyPostalAddressCountry);

                //cac:DeliveryCustomerParty/cac:Party/cac:PostalAddress/cac:Country/IdentificationCode
                Element deliveryCustomerPartyPartyPostalAddressCountryIdentificationCode = doc.createElement("cbc:IdentificationCode");
                deliveryCustomerPartyPartyPostalAddressCountry.appendChild(deliveryCustomerPartyPartyPostalAddressCountryIdentificationCode);
                deliveryCustomerPartyPartyPostalAddressCountryIdentificationCode.appendChild(doc.createTextNode(EmpresaProperties.getInstance().getCodigoPais()));


            }

            if(guiaRemision.getDestinatario().getRazonSocial()!=null){
                //cac:DeliveryCustomerParty/cac:Party/cac:PartyLegalEntity
                Element deliveryCustomerPartyPartyPartyLegalEntity = doc.createElement("cac:PartyLegalEntity");
                deliveryCustomerPartyParty.appendChild(deliveryCustomerPartyPartyPartyLegalEntity);
                Element deliveryCustomerPartyPartyPartyLegalEntityRegistrationName = doc.createElement("cbc:RegistrationName");
                deliveryCustomerPartyPartyPartyLegalEntity.appendChild(deliveryCustomerPartyPartyPartyLegalEntityRegistrationName);
                deliveryCustomerPartyPartyPartyLegalEntityRegistrationName.appendChild(doc.createTextNode(guiaRemision.getDestinatario().getRazonSocial()));
            }


            //cac:Shipment
            Element shipment = doc.createElement("cac:Shipment");
            invoice.appendChild(shipment);
            //cac:Shipment/cbc:ID
            Element shipmentID = doc.createElement("cbc:ID");
            shipment.appendChild(shipmentID);
            shipmentID.appendChild(doc.createTextNode("SUNAT_Envio"));
            //cac:Shipment/cbc:HandlingCode
            Element shipmentHandlingCode = doc.createElement("cbc:HandlingCode");
            shipment.appendChild(shipmentHandlingCode);
            shipmentHandlingCode.appendChild(doc.createTextNode(guiaRemision.getEnvio().getMotivoTraslado().getCodigoSunat()));

            if(guiaRemision.getEnvio().getMotivoTraslado().getCodigoSunat().equals("13")){
                //cac:Shipment/cbc:HandlingCode
                Element shipmentHandlingInstructions= doc.createElement("cbc:HandlingInstructions");
                shipment.appendChild(shipmentHandlingInstructions);
                shipmentHandlingInstructions.appendChild(doc.createTextNode(guiaRemision.getEnvio().getDescripcionMotivoTraslado()));
            }
            if(guiaRemision.getEnvio().getMotivoTraslado().getCodigoSunat().equals(GuiasConstans.CODIGO_IMPORTACION) ||
                    guiaRemision.getEnvio().getMotivoTraslado().getCodigoSunat().equals(GuiasConstans.CODIGO_EXPORTACION)){
                //cac:Shipment/cbc:Information
                Element shipmentInformation = doc.createElement("cbc:Information");
                shipment.appendChild(shipmentInformation);
                shipmentInformation.appendChild(doc.createTextNode(guiaRemision.getEnvio().getMotivoTraslado().getCodigoSunat()));
            }

            //cac:Shipment/cbc:GrossWeightMeasure
            Element shipmentGrossWeightMeasure = doc.createElement("cbc:GrossWeightMeasure");
            shipment.appendChild(shipmentGrossWeightMeasure);
            shipmentGrossWeightMeasure.setAttribute("unitCode", guiaRemision.getEnvio().getUnidadMedidaPesoBruto().getCodigoSunat());



            shipmentGrossWeightMeasure.appendChild(doc.createTextNode(BigDecimal.valueOf(guiaRemision.getEnvio().getPesoBruto()).setScale(3,RoundingMode.CEILING).toPlainString()));



            //cac:Shipment/cac:ShipmentStage
            var shipmentShipmentStage= doc.createElement("cac:ShipmentStage");
            shipment.appendChild(shipmentShipmentStage);
            //cac:Shipment/cac:ShipmentStage/cbc:TransportModeCode
            var shipmentShipmentStageTransportModeCode = doc.createElement("cbc:TransportModeCode");
            shipmentShipmentStage.appendChild(shipmentShipmentStageTransportModeCode);
            shipmentShipmentStageTransportModeCode.appendChild(doc.createTextNode(guiaRemision.getEnvio().getModalidadTraslado().getCodigoSunat()));
            //cac:Shipment/cac:ShipmentStage/cac:TransitPeriod
            var shipmentShipmentStageTransitPeriod = doc.createElement("cac:TransitPeriod");
            shipmentShipmentStage.appendChild(shipmentShipmentStageTransitPeriod);
            var shipmentShipmentStageTransitPeriodStartDate = doc.createElement("cbc:StartDate");
            shipmentShipmentStageTransitPeriod.appendChild(shipmentShipmentStageTransitPeriodStartDate);
            shipmentShipmentStageTransitPeriodStartDate.appendChild(doc.createTextNode(Fecha.toStringMySQL(guiaRemision.getFechaEmision())));
            //cac:Shipment/cac:ShipmentStage/cac:TransportMeans
            //var shipmentShipmentStageTransportMeans = doc.createElement("cac:TransportMeans");
            //shipmentShipmentStage.appendChild(shipmentShipmentStageTransportMeans);
            //cac:Shipment/cac:ShipmentStage/cac:TransportMeans/cac:RoadTransport
            //var shipmentShipmentStageTransportMeansRoadTransport = doc.createElement("cac:RoadTransport");
            //shipmentShipmentStageTransportMeans.appendChild(shipmentShipmentStageTransportMeansRoadTransport);
            //cac:Shipment/cac:ShipmentStage/cac:TransportMeans/cac:RoadTransport/cbc:LicensePlateID
            //var shipmentShipmentStageTransportMeansLicensePlateID = doc.createElement("cbc:LicensePlateID");
            //shipmentShipmentStageTransportMeans.appendChild(shipmentShipmentStageTransportMeansLicensePlateID);

            //cac:Shipment/cac:Delivery
            var shipmentDelivery = doc.createElement("cac:Delivery");
            shipment.appendChild(shipmentDelivery);

            if(guiaRemision.getPuntoLlegada() != null){
                //cac:Shipment/cac:Delivery/cac:DeliveryAddress
                var shipmentDeliverDeliveryAddress = doc.createElement("cac:DeliveryAddress");
                shipmentDelivery.appendChild(shipmentDeliverDeliveryAddress);

                //cac:Shipment/cac:Delivery/cac:DeliveryAddress/cbc:ID
                var shipmentDeliveryDeliveryAddressID = doc.createElement("cbc:ID");
                shipmentDeliverDeliveryAddress.appendChild(shipmentDeliveryDeliveryAddressID);
                shipmentDeliveryDeliveryAddressID.appendChild(doc.createTextNode(guiaRemision.getPuntoLlegada().getDistrito().getCodigoUbigeo()));

                if(guiaRemision.getEnvio().getMotivoTraslado().getId().equals(12)){
                    //cac:Shipment/cac:Delivery/cac:DeliveryAddress/cbc:AddressTypeCode
                    var shipmentDeliverDeliveryAddressAddressTypeCode = doc.createElement("cbc:AddressTypeCode");
                    shipmentDeliverDeliveryAddress.appendChild(shipmentDeliverDeliveryAddressAddressTypeCode);

                    shipmentDeliverDeliveryAddressAddressTypeCode.setAttribute("listID", EmpresaProperties.getInstance().getRuc());
                    shipmentDeliverDeliveryAddressAddressTypeCode.appendChild(doc.createTextNode(guiaRemision.getPuntoLlegada().getAlmacen().getCodigoSunat()));
                }

                //cac:Shipment/cac:Delivery/cac:DeliveryAddress/cbc:CityName
                var shipmentDeliverDeliveryAddressCityName = doc.createElement("cbc:CityName");
                shipmentDeliverDeliveryAddress.appendChild(shipmentDeliverDeliveryAddressCityName);
                shipmentDeliverDeliveryAddressCityName.appendChild(doc.createTextNode(guiaRemision.getPuntoLlegada().getDistrito().getProvincia().getDepartamento().getDescripcion()));

                //cac:Shipment/cac:Delivery/cac:DeliveryAddress/CountrySubentity
                var  shipmentDeliverDeliveryAddressCountrySubentity = doc.createElement("cbc:CountrySubentity");
                shipmentDeliverDeliveryAddress.appendChild(shipmentDeliverDeliveryAddressCountrySubentity);
                shipmentDeliverDeliveryAddressCountrySubentity.appendChild(doc.createTextNode(guiaRemision.getPuntoLlegada().getDistrito().getProvincia().getDescripcion()));

                //cac:Shipment/cac:Delivery/cac:DeliveryAddress/District
                var shipmentDeliverDeliveryAddressDistrict = doc.createElement("cbc:District");
                shipmentDeliverDeliveryAddress.appendChild(shipmentDeliverDeliveryAddressDistrict);
                shipmentDeliverDeliveryAddressDistrict.appendChild(doc.createTextNode(guiaRemision.getPuntoLlegada().getDistrito().getDescripcion()));

                //cac:Shipment/cac:Delivery/cac:DeliveryAddress/cac:AddressLine
                var shipmentDeliverDeliveryAddressAddressLine = doc.createElement("cac:AddressLine");
                shipmentDeliverDeliveryAddress.appendChild(shipmentDeliverDeliveryAddressAddressLine);

                //cac:Shipment/cac:Delivery/cac:DeliveryAddress/cac:AddressLine/cbc:Line
                var shipmentDeliverDeliveryAddressAddressLineLine = doc.createElement("cbc:Line");
                shipmentDeliverDeliveryAddressAddressLine.appendChild(shipmentDeliverDeliveryAddressAddressLineLine);
                shipmentDeliverDeliveryAddressAddressLineLine.appendChild(doc.createTextNode(guiaRemision.getPuntoLlegada().getDireccion()));

                //cac:Shipment/cac:Delivery/cac:DeliveryAddress/cac:Country
                var shipmentDeliverDeliveryAddressCountry = doc.createElement("cac:Country");
                shipmentDeliverDeliveryAddress.appendChild(shipmentDeliverDeliveryAddressCountry);

                var shipmentDeliverDeliveryAddressCountryIdentificationCode = doc.createElement("cbc:IdentificationCode");
                shipmentDeliverDeliveryAddressCountry.appendChild(shipmentDeliverDeliveryAddressCountryIdentificationCode);
                shipmentDeliverDeliveryAddressCountryIdentificationCode.appendChild(doc.createTextNode(CODIGO_PAIS_PERU));
            }

            //cac:Shipment/cac:Delivery/cac:Despatch
            Element shipmentDeliveryDespatch = doc.createElement("cac:Despatch");
            shipmentDelivery.appendChild(shipmentDeliveryDespatch);


            //cac:Shipment/cac:Delivery/cac:DespatchAddress
            var shipmentDeliveryDespatchDespatchAddress = doc.createElement("cac:DespatchAddress");
            shipmentDeliveryDespatch.appendChild(shipmentDeliveryDespatchDespatchAddress);

            //cac:Shipment/cac:Delivery/cac:DespatchAddress/cbc:ID
            var shipmentDeliveryDespatchDespatchAddressID = doc.createElement("cbc:ID");
            shipmentDeliveryDespatchDespatchAddress.appendChild(shipmentDeliveryDespatchDespatchAddressID);
            shipmentDeliveryDespatchDespatchAddressID.appendChild(doc.createTextNode(guiaRemision.getPuntoPartida().getDistrito().getCodigoUbigeo()));

            if(guiaRemision.getEnvio().getMotivoTraslado().getId().equals(12)){
                //cac:Shipment/cac:Delivery/cac:DespatchAddress/cbc:AddressTypeCode
                var shipmentDeliverDespatchAddressAddressTypeCode = doc.createElement("cbc:AddressTypeCode");
                shipmentDeliveryDespatchDespatchAddress.appendChild(shipmentDeliverDespatchAddressAddressTypeCode);

                shipmentDeliverDespatchAddressAddressTypeCode.setAttribute("listID", EmpresaProperties.getInstance().getRuc());
                shipmentDeliverDespatchAddressAddressTypeCode.appendChild(doc.createTextNode(guiaRemision.getPuntoPartida().getAlmacen().getCodigoSunat()));
            }

            //cac:Shipment/cac:Delivery/cac:DespatchAddress/cbc:CityName
            var shipmentDeliveryDespatchDespatchAddressCityName = doc.createElement("cbc:CityName");
            shipmentDeliveryDespatchDespatchAddress.appendChild(shipmentDeliveryDespatchDespatchAddressCityName);
            shipmentDeliveryDespatchDespatchAddressCityName.appendChild(doc.createTextNode(guiaRemision.getPuntoPartida().getDistrito().getProvincia().getDepartamento().getDescripcion()));

            //cac:Shipment/cac:Delivery/cac:DespatchAddress/cbc:CountrySubentity
            var shipmentDeliveryDespatchDespatchAddressCountrySubentity = doc.createElement("cbc:CountrySubentity");
            shipmentDeliveryDespatchDespatchAddress.appendChild(shipmentDeliveryDespatchDespatchAddressCountrySubentity);
            shipmentDeliveryDespatchDespatchAddressCountrySubentity.appendChild(doc.createTextNode(guiaRemision.getPuntoPartida().getDistrito().getProvincia().getDescripcion()));

            //cac:Shipment/cac:Delivery/cac:DespatchAddress/District
            var shipmentDeliveryDespatchDespatchAddressDistrict = doc.createElement("cbc:District");
            shipmentDeliveryDespatchDespatchAddress.appendChild(shipmentDeliveryDespatchDespatchAddressDistrict);
            shipmentDeliveryDespatchDespatchAddressDistrict.appendChild(doc.createTextNode(guiaRemision.getPuntoPartida().getDistrito().getDescripcion()));

            //cac:Shipment/cac:Delivery/cac:DespatchAddress/cac:AddressLine
            var shipmentDeliveryDespatchDespatchAddressAddressLine= doc.createElement("cac:AddressLine");
            shipmentDeliveryDespatchDespatchAddress.appendChild(shipmentDeliveryDespatchDespatchAddressAddressLine);
            //cac:Shipment/cac:Delivery/cac:DespatchAddress/cac:AddressLine/cbc:Line
            var shipmentDeliveryDespatchDespatchAddressAddressLineLine = doc.createElement("cbc:Line");
            shipmentDeliveryDespatchDespatchAddressAddressLine.appendChild(shipmentDeliveryDespatchDespatchAddressAddressLineLine);
            shipmentDeliveryDespatchDespatchAddressAddressLineLine.appendChild(doc.createTextNode(guiaRemision.getPuntoPartida().getDireccion()));

            //cac:Shipment/cac:Delivery/cac:DespatchAddress/cac:Country
            var shipmentDeliveryDespatchDespatchAddressCountry = doc.createElement("cac:Country");
            shipmentDeliveryDespatchDespatchAddress.appendChild(shipmentDeliveryDespatchDespatchAddressCountry);

            //cac:Shipment/cac:Delivery/cac:DespatchAddress/cac:Country/cbc:IdentificationCode
            var shipmentDeliveryDespatchDespatchAddressCountryIdentificationCode = doc.createElement("cbc:IdentificationCode");
            shipmentDeliveryDespatchDespatchAddressCountry.appendChild(shipmentDeliveryDespatchDespatchAddressCountryIdentificationCode);
            shipmentDeliveryDespatchDespatchAddressCountryIdentificationCode.appendChild(doc.createTextNode(CODIGO_PAIS_PERU));


            if(!guiaRemision.getVehiculos().isEmpty()){
                //cac:TransportHandlingUnit
                var transportHandlingUnit = doc.createElement("cac:TransportHandlingUnit");
                //cac:TransportHandlingUnit/cac:TransportEquipment
                var transportHandlingUnitTransportEquipment = doc.createElement("cac:TransportEquipment");
                //cac:TransportHandlingUnit/cac:TransportEquipment/cbc:ID
                var transportHandlingUnitTransportEquipmentID = doc.createElement("cbc:ID");
                transportHandlingUnitTransportEquipmentID.appendChild(doc.createTextNode(guiaRemision.getVehiculos().get(0).getNumeroPlaca()));
                transportHandlingUnitTransportEquipment.appendChild(transportHandlingUnitTransportEquipmentID);

                if(guiaRemision.getVehiculos().size()>1){
                    for(int i=1;i<guiaRemision.getVehiculos().size();i++){
                        var vehiculoSecundario = guiaRemision.getVehiculos().get(i);
                        //cac:TransportHandlingUnit/cac:TransportEquipment/cac:AttachedTransportEquipment
                        var transportHandlingUnitTransportEquipmentAttachedTransportEquipment = doc.createElement("cac:AttachedTransportEquipment");
                        //cac:TransportHandlingUnit/cac:TransportEquipment/cac:AttachedTransportEquipment/cbc:ID
                        var transportHandlingUnitTransportEquipmentAttachedTransportEquipmentID = doc.createElement("cbc:ID");
                        transportHandlingUnitTransportEquipmentAttachedTransportEquipmentID.appendChild(doc.createTextNode(vehiculoSecundario.getNumeroPlaca()));
                        transportHandlingUnitTransportEquipmentAttachedTransportEquipment.appendChild(transportHandlingUnitTransportEquipmentAttachedTransportEquipmentID);
                        transportHandlingUnitTransportEquipment.appendChild(transportHandlingUnitTransportEquipmentAttachedTransportEquipment);
                    }
                }

                transportHandlingUnit.appendChild(transportHandlingUnitTransportEquipment);
                shipment.appendChild(transportHandlingUnit);
            }

            if(!guiaRemision.getConductores().isEmpty()){

                var conductoPrincipalOp = guiaRemision.getConductores().stream().findFirst();
                var conductorPrincipal = conductoPrincipalOp.get();
                agregarConductor(shipmentShipmentStage,conductorPrincipal,GuiasConstans.CONDUCTOR_PRINCIPAL, doc);

                for (int i1 = 1; i1 < guiaRemision.getConductores().size(); i1++) {
                    agregarConductor(shipmentShipmentStage,guiaRemision.getConductores().get(i1),GuiasConstans.CONDUCTOR_SECUNDARIO, doc);
                }
            }

            if(!guiaRemision.getArticulos().isEmpty()){
                final int[] secuencia = {1};
                guiaRemision.getArticulos().forEach(art->{
                    //cac:DespatchLine
                    var despatchLine = doc.createElement("cac:DespatchLine");
                    invoice.appendChild(despatchLine);

                    //cac:DespatchLine/cbc:ID
                    var despatchLineID = doc.createElement("cbc:ID");
                    despatchLine.appendChild(despatchLineID);
                    despatchLineID.appendChild(doc.createTextNode(String.valueOf(secuencia[0])));
                    secuencia[0]++;

                    if(art.getUnidadMedida()!=null){
                        //cac:DespatchLine/cbc:Note
                        var despatchLineNote = doc.createElement("cbc:Note");
                        despatchLine.appendChild(despatchLineNote);
                        despatchLineNote.appendChild(doc.createTextNode(art.getUnidadMedida().getDescripcion()));
                    }

                    //cac:DespatchLine/cbc:DeliveredQuantity
                    var despatchLineDeliveredQuantity = doc.createElement("cbc:DeliveredQuantity");
                    despatchLine.appendChild(despatchLineDeliveredQuantity);
                    despatchLineDeliveredQuantity.setAttribute("unitCode","ZZ");
                    despatchLineDeliveredQuantity.appendChild(doc.createTextNode(BigDecimal.valueOf(art.getCantidad()).setScale(4,RoundingMode.CEILING).toPlainString()));

                    //cac:DespatchLine/cac:OrderLineReference
                    var despatchLineOrderLineReference = doc.createElement("cac:OrderLineReference");
                    despatchLine.appendChild(despatchLineOrderLineReference);
                    //cac:DespatchLine/cac:OrderLineReference/cbc:LineID
                    var despatchLineOrderLineReferenceLineID = doc.createElement("cbc:LineID");
                    despatchLineOrderLineReference.appendChild(despatchLineOrderLineReferenceLineID);
                    despatchLineOrderLineReferenceLineID.appendChild(doc.createTextNode(String.valueOf(secuencia)));

                    //cac:DespatchLine/cac:Item
                    var despatchLineItem = doc.createElement("cac:Item");
                    despatchLine.appendChild(despatchLineItem);
                    //cac:DespatchLine/cac:Item/cbc:Name
                    var despatchLineItemName = doc.createElement("cbc:Name");
                    despatchLineItem.appendChild(despatchLineItemName);
                    despatchLineItemName.appendChild(doc.createTextNode(art.getDescripcion()));
                    //cac:DespatchLine/cac:Item/cac:SellersItemIdentification
                    var despatchLineItemSellersItemIdentification = doc.createElement("cac:SellersItemIdentification");
                    despatchLineItem.appendChild(despatchLineItemSellersItemIdentification);

                    //cac:DespatchLine/cac:Item/cac:SellersItemIdentification/cbc:ID
                    var despatchLineItemSellersItemIdentificationID = doc.createElement("cbc:ID");
                    despatchLineItemSellersItemIdentification.appendChild(despatchLineItemSellersItemIdentificationID);
                    despatchLineItemSellersItemIdentificationID.appendChild(doc.createTextNode(art.getCodigo()));


                });
            }

            FileOutputStream fileOutputStream = new FileOutputStream(xmlFile);
            var transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

            StreamResult streamResult = new StreamResult(fileOutputStream);
            transformer.transform(new DOMSource(doc),streamResult);
            streamResult.getOutputStream().close();

            return true;


        }catch (IOException | IllegalArgumentException | ParserConfigurationException | TransformerException |
                DOMException ex){
            throw new XMLGuiaRemisionException("Error al generar el archivo XML, ", ex);
        }catch (Exception ex){
            throw new XMLGuiaRemisionException("Error inesperado al generar el archivo xml: ", ex);
        }

    }

    private void agregarConductor(Element shipmentShipmentStage, Conductor conductor, String tipo, Document doc){
        //cac:Shipment/cac:ShipmentStage/cac:DriverPerson
        var shipmentShipmentStageDriverPerson = doc.createElement("cac:DriverPerson");
        shipmentShipmentStage.appendChild(shipmentShipmentStageDriverPerson);

        //cac:Shipment/cac:ShipmentStage/cac:DriverPerson/cbc:JobTitle
        var shipmentShipmentStageDriverPersonJobTitle = doc.createElement("cbc:JobTitle");
        shipmentShipmentStageDriverPersonJobTitle.appendChild(doc.createTextNode(tipo));
        shipmentShipmentStageDriverPerson.appendChild(shipmentShipmentStageDriverPersonJobTitle);

        //cac:Shipment/cac:ShipmentStage/cac:DriverPerson/cbc:ID
        var shipmentShipmentStageDriverPersonID = doc.createElement("cbc:ID");
        shipmentShipmentStageDriverPersonID.setAttribute("schemeID","1");
        shipmentShipmentStageDriverPerson.appendChild(shipmentShipmentStageDriverPersonID);
        shipmentShipmentStageDriverPersonID.appendChild(doc.createTextNode(conductor.getNroDocumentoIdentidad()));
        //cac:Shipment/cac:ShipmentStage/cac:DriverPerson/cbc:FirstName
        var shipmentShipmentStageDriverPersonFirstName = doc.createElement("cbc:FirstName");
        shipmentShipmentStageDriverPerson.appendChild(shipmentShipmentStageDriverPersonFirstName);
        shipmentShipmentStageDriverPersonFirstName.appendChild(doc.createTextNode(conductor.getNombres()));
        //cac:Shipment/cac:ShipmentStage/cac:DriverPerson/cbc:FamilyName
        var shipmentShipmentStageDriverPersonFamilyName = doc.createElement("cbc:FamilyName");
        shipmentShipmentStageDriverPerson.appendChild(shipmentShipmentStageDriverPersonFamilyName);
        shipmentShipmentStageDriverPersonFamilyName.appendChild(doc.createTextNode(conductor.getApellidos()));

        //cac:Shipment/cac:ShipmentStage/cac:DriverPerson/cac:IdentityDocumentReference
        var shipmentShipmentStageDriverPersonIdentityDocumentReference = doc.createElement("cac:IdentityDocumentReference");
        shipmentShipmentStageDriverPerson.appendChild(shipmentShipmentStageDriverPersonIdentityDocumentReference);
        var shipmentShipmentStageDriverPersonIdentityDocumentReferenceID = doc.createElement("cbc:ID");
        shipmentShipmentStageDriverPersonIdentityDocumentReference.appendChild(shipmentShipmentStageDriverPersonIdentityDocumentReferenceID);
        shipmentShipmentStageDriverPersonIdentityDocumentReferenceID.appendChild(doc.createTextNode(conductor.getNroLicenciaBrevete()));

    }
}
