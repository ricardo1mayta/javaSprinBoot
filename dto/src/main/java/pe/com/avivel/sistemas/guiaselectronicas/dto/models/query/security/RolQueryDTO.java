package pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.security;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class RolQueryDTO {

    private Integer id;

    private String nombre;

    private String tipo;

    private boolean activo;
}
