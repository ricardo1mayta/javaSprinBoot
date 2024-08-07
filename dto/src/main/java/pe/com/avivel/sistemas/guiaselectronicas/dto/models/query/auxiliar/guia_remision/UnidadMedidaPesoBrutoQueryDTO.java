package pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.auxiliar.guia_remision;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UnidadMedidaPesoBrutoQueryDTO {

    private Integer id;

    private String codigoSunat;

    private String abreviatura;

    private String descripcion;
}
