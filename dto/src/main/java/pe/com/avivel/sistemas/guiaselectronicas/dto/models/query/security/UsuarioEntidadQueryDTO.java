package pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.security;

import lombok.Data;
import lombok.NoArgsConstructor;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.maestros.entidad.EntidadSimpleQueryDTO;

import java.util.Date;

@Data
@NoArgsConstructor
public class UsuarioEntidadQueryDTO {

    private Integer id;

    private EntidadSimpleQueryDTO entidad;

    private Date fechaIngreso;
}
