package pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.auxiliar;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TipoDocumentoQueryDTO {

    private Integer id;

    private String codigoSunat;

    private String abreviatura;

    private String descripcion;
}
