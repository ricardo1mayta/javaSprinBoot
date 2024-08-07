package pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.auxiliar.guia_remision;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UnidadMedidaQueryDTO {

    private Integer id;

    private String codigoSunat;

    private String descripcionSunat;

    private String abreviatura;

    private String descripcion;
}
