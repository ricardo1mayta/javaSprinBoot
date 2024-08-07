package pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.maestros;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class SerieQueryDTO {

    private Integer id;

    private String codigo;

    private String abreviatura;

    private String descripcion;

    @JsonProperty("fechaRegistro")
    private Date creationDate;

    private boolean activo;
}
