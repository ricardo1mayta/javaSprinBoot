package pe.com.avivel.sistemas.guiaselectronicas.efact.service;

import pe.com.avivel.sistemas.guiaselectronicas.commons.efact.ResultEnvioGuiaOSE;
import pe.com.avivel.sistemas.guiaselectronicas.commons.efact.ResultObtenerCDR;
import pe.com.avivel.sistemas.guiaselectronicas.efact.exceptions.EfactException;
import pe.com.avivel.sistemas.guiaselectronicas.entities.guia_remision.guia_remision_remitente.GuiaRemisionRemitente;


public interface GuiasElectronicasEfact {

    String obtenerTokenAutorizacion() throws EfactException;

    ResultEnvioGuiaOSE enviarGuiaRemision(String nombreGuia) throws EfactException;

    String enviarGuiaRemision(Integer guiaRemisionId) throws EfactException;

    ResultObtenerCDR obtenerCDR(String ticket, GuiaRemisionRemitente guia) throws EfactException;

    byte[] obtenerXML(String ticket) throws EfactException;

    byte[] obtenerPDF(String ticket) throws EfactException;
}
