package pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.maestros;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ArticuloSimpleQueryDTO {

    private Integer id;

    private String codigo;

    private String descripcion;
}