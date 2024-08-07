package pe.com.avivel.sistemas.guiaselectronicas.restapi.response;

import lombok.Getter;
import pe.com.avivel.sistemas.guiaselectronicas.restapi.config.BeansConfig;

import java.io.Serializable;
import java.util.Date;

@Getter
public class ResponseData<E> implements Serializable {

    private static final long serialVersionUID = -7448118514599547457L;
    private E data;
    private String message = "";
    private String details = "";
    private final Date timestamp = new Date();
    private final String apiNombre = BeansConfig.API_NOMBRE;
    private final String apiVersion = BeansConfig.API_VERSION;

    public ResponseData(E data) {
        this.data = data;
    }

    public ResponseData(String message, String details) {
        this.message = message;
        this.details = details;
    }

    public ResponseData(E data, String message, String details) {
        this.data = data;
        this.message = message;
        this.details = details;
    }
}