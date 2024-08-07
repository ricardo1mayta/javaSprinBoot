package pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.ubigeos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DepartamentoQueryDTO {

    private Integer id;

    private String codigoUbigeo;

    private String abreviatura;

    private String descripcion;

    private Integer paisId;
}
