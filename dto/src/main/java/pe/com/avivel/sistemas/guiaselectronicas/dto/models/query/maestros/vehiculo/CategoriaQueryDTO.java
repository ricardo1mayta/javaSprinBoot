package pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.maestros.vehiculo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CategoriaQueryDTO {

    private Integer id;

    private String codigo;

    private String descripcion;
}
