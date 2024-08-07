package pe.com.avivel.sistemas.guiaselectronicas.commons.empresa;


import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;


public class EmpresaProperties {

    private static EmpresaProperties INSTANCE = null;
    private String ruc;
    private Properties properties;

    private EmpresaProperties(){
        String empresaPropertiesPath =  EmpresaProperties.class.getClassLoader()
                .getResource("empresa.properties").getPath();
        properties = new Properties();
        try {
            properties.load(new FileInputStream(empresaPropertiesPath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static  EmpresaProperties getInstance(){
        if(INSTANCE==null){
            return new EmpresaProperties();
        }else {
            return INSTANCE;
        }
    }

    public Integer getTipoDocumentoIdentidadId(){
        return Integer.valueOf(properties.getProperty("empresa.tipodocumentoidentidadid"));
    }

    public String getRuc(){
        return properties.getProperty("empresa.ruc");
    }

    public String getRasonSocial(){
        return properties.getProperty("empresa.razonsocial");
    }

    public String getDireccion(){
        return properties.getProperty("empresa.direccion");
    }

    public String getDepartamento(){
        return properties.getProperty("empresa.departamento");
    }

    public String getProvincia(){
        return properties.getProperty("empresa.provincia");
    }

    public String getDistrito(){
        return properties.getProperty("empresa.distrito");
    }

    public String getDistritoUbigeo(){
        return properties.getProperty("empresa.distrito.ubigeo");
    }

    public String getCodigoPais(){
        return properties.getProperty("empresa.codigopais");
    }
}
