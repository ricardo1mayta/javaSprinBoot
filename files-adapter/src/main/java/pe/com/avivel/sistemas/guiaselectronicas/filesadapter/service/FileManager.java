package pe.com.avivel.sistemas.guiaselectronicas.filesadapter.service;

import pe.com.avivel.sistemas.guiaselectronicas.commons.files.TypeFile;
import pe.com.avivel.sistemas.guiaselectronicas.filesadapter.exceptions.FilesAdapterException;

public interface FileManager {


    boolean save(byte[] buffer, String filename, TypeFile typeFile) throws FilesAdapterException;

    byte[] downloadFile(String filename, TypeFile typeFile) throws FilesAdapterException;


}
