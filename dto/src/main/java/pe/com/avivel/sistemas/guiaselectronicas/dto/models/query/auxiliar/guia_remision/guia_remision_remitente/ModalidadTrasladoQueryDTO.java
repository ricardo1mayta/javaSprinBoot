package pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.auxiliar.guia_remision.guia_remision_remitente;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ModalidadTrasladoQueryDTO {

    private Integer id;

    private String codigoSunat;

    private String abreviatura;

    private String descripcion;
}
