package pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.auxiliar.guia_remision;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@NoArgsConstructor
public class EstadoGuiaQueryDTO {

    private Integer id;

    private String abreviatura;

    private String descripcion;

    private String observacion;
}
