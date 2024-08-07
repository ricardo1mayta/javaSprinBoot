package pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.maestros;

import lombok.Data;
import lombok.NoArgsConstructor;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.auxiliar.guia_remision.UnidadMedidaQueryDTO;

import java.util.Date;

@Data
@NoArgsConstructor
public class ArticuloUnidadMedidaQueryDTO {

    private Integer id;

    private UnidadMedidaQueryDTO unidadMedida;

    private Date fechaIngreso;

    private Integer articuloId;
}
