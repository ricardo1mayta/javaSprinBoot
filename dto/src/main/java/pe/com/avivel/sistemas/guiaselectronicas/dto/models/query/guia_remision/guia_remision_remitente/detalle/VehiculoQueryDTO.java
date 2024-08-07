package pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.guia_remision.guia_remision_remitente.detalle;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class VehiculoQueryDTO {

    private Integer id;

    private Integer nroOrden;

    private String numeroPlaca;

    private String descripcion;

    private String entidadAutorizadora;

    private String nroAutorizacion;
}
