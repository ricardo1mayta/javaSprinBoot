package pe.com.avivel.sistemas.guiaselectronicas.filesadapter.service;

import pe.com.avivel.sistemas.guiaselectronicas.entities.guia_remision.GuiaRemision;
import pe.com.avivel.sistemas.guiaselectronicas.entities.guia_remision.guia_remision_remitente.GuiaRemisionRemitente;
import pe.com.avivel.sistemas.guiaselectronicas.filesadapter.exceptions.XMLGuiaRemisionException;

@FunctionalInterface
public interface XMLGuiaRemision {

    boolean generarXML(GuiaRemisionRemitente guiaRemision) throws XMLGuiaRemisionException;

}
