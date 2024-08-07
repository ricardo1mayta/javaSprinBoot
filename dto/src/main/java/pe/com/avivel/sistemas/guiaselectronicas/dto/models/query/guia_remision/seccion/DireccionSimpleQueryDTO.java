package pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.guia_remision.seccion;

import lombok.Data;
import lombok.NoArgsConstructor;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.ubigeos.DistritoQueryDTO;

@Data
@NoArgsConstructor
public class DireccionSimpleQueryDTO {

    private DistritoQueryDTO distrito;

    private String direccion;
}
