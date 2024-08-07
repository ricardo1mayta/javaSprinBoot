package pe.com.avivel.sistemas.guiaselectronicas.commons.files;

import lombok.Getter;

@Getter
public enum TypeFile {
    XML_GUIA_UNSIGNED(".xml"),
    XML_GUIA_SIGNED(".xml"),
    CDR_GUIA(".xml"),
    PDF_GUIA(".pdf");

    private final String extension;

    TypeFile(String extension){
        this.extension= extension;
    }
}
