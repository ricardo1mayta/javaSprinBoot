package pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.maestros.entidad;

import lombok.Data;
import lombok.NoArgsConstructor;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.ubigeos.DistritoQueryDTO;

@Data
@NoArgsConstructor
public class EntidadAlmacenQueryDTO {

    private Integer id;

    private String descripcion;

    private DistritoQueryDTO distrito;

    private String direccion;

    private Integer entidadId;

    private boolean activo;
}
