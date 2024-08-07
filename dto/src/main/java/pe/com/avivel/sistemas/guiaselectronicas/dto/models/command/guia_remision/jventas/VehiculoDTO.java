package pe.com.avivel.sistemas.guiaselectronicas.dto.models.command.guia_remision.jventas;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class VehiculoDTO {

    private Integer nroOrden;
    private String numeroPlaca;
    private String constanciaMTC;
    private String descripcion;
}
