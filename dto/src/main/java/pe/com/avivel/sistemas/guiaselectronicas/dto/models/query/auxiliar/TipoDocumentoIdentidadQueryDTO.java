package pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.auxiliar;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@NoArgsConstructor
public class TipoDocumentoIdentidadQueryDTO {

    private Integer id;

    private String codigoSunat;

    private String abreviatura;

    private String descripcion;
}
