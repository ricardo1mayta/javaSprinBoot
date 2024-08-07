package pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.security;

import lombok.Data;
import lombok.NoArgsConstructor;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.maestros.ArticuloSimpleQueryDTO;

import java.util.Date;

@Data
@NoArgsConstructor
public class UsuarioArticuloQueryDTO {

    private Integer id;

    private ArticuloSimpleQueryDTO articulo;

    private Date fechaIngreso;
}
