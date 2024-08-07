package pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.ubigeos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DistritoQueryDTO {

    private Integer id;

    private String codigoUbigeo;

    private String abreviatura;

    private String descripcion;

    private Integer provinciaId;

    @JsonProperty("provincia")
    private String provinciaDescripcion;

    @JsonProperty("departamentoId")
    private Integer provinciaDepartamentoId;

    @JsonProperty("departamento")
    private String provinciaDepartamentoDescripcion;
}
