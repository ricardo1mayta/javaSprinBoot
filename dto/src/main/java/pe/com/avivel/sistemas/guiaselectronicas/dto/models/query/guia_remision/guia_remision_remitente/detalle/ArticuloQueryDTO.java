package pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.guia_remision.guia_remision_remitente.detalle;

import lombok.Data;
import lombok.NoArgsConstructor;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.auxiliar.guia_remision.UnidadMedidaQueryDTO;

@Data
@NoArgsConstructor
public class ArticuloQueryDTO {

    private Integer id;

    private Integer nroOrden;

    private Double cantidad;

    private UnidadMedidaQueryDTO unidadMedida;

    private String codigo;

    private String descripcion;

    private Double peso;

    private String observacion;
}
