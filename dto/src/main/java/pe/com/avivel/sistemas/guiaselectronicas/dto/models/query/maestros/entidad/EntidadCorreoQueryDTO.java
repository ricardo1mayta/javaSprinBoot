package pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.maestros.entidad;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EntidadCorreoQueryDTO {

    private Integer id;

    private String correo;

    private Integer entidadId;

    private boolean activo;
}
