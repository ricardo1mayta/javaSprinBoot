package pe.com.avivel.sistemas.guiaselectronicas.filesadapter.service.impl;

import org.springframework.stereotype.Service;
import pe.com.avivel.sistemas.guiaselectronicas.commons.files.FileRoutes;
import pe.com.avivel.sistemas.guiaselectronicas.commons.files.TypeFile;
import pe.com.avivel.sistemas.guiaselectronicas.filesadapter.exceptions.FilesAdapterException;
import pe.com.avivel.sistemas.guiaselectronicas.filesadapter.service.FileManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

@Service
public class FileManagerImpl implements FileManager {


    @Override
    public boolean save(byte[] buffer, String filename, TypeFile typeFile) throws FilesAdapterException {

        try{
            String pathFile=this.path(filename, typeFile);
            this.saveFile(buffer, pathFile);
            return true;

        }catch (IOException ex){
            throw new FilesAdapterException("Error al guardar el archivo tipo: "+typeFile+", " +ex);
        }
    }

    @Override
    public byte[] downloadFile(String filename, TypeFile typeFile) throws FilesAdapterException {
        try{
            String pathFile=this.path(filename, typeFile);
            var file = new File(pathFile);
            return Files.readAllBytes(file.toPath());

        }catch (IOException ex){
            throw new FilesAdapterException("Error al descargar el archivo de tipo: "+typeFile+", "+ex);
        }
    }

    private String path( String filename,TypeFile typeFile){

        String pathFile="";
        switch (typeFile){
            case XML_GUIA_UNSIGNED:{
                pathFile= FileRoutes.UNSIGNED_XML_FOLDER + filename;
                break;
            }
            case XML_GUIA_SIGNED:{
                pathFile= FileRoutes.SIGNED_XML_FOLDER + filename;
                break;
            }
            case CDR_GUIA:{
                pathFile= FileRoutes.CDR_XML_FOLDER+filename;
                break;
            }
            case PDF_GUIA:{
                pathFile= FileRoutes.PDF_FOLDER+filename;
                break;
            }
            default:{
                pathFile= "Error path";
            }

        }

        return pathFile;
    }

    private void saveFile(byte[] buffer,String pathFile) throws IOException{
        var xmlFile = new File(pathFile);
        FileOutputStream stream = null;
        stream = new FileOutputStream(xmlFile);
        stream.write(buffer);
        stream.close();
    }
}
