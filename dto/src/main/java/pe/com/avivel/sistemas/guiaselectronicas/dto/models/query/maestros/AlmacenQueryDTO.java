package pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.maestros;

import lombok.Data;
import lombok.NoArgsConstructor;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.ubigeos.DistritoQueryDTO;

@Data
@NoArgsConstructor
public class AlmacenQueryDTO {

    private Integer id;

    private String codigoSunat;

    private String descripcion;

    private DistritoQueryDTO distrito;

    private String direccion;

    private String observacion;

    private boolean activo;
}
