package pe.com.avivel.sistemas.guiaselectronicas.dto.models.command.guia_remision.jventas;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DetalleGuiaRemisionDTO {

    private Integer nroOrden;
    private Double cantidad;
    private String codigo;
    private String descripcion;
    private String observacion;
    private String unidadCodigoSunat;
    private Double peso;
}
