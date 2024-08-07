package pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.guia_remision.seccion;

import lombok.Data;
import lombok.NoArgsConstructor;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.maestros.AlmacenQueryDTO;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.maestros.entidad.EntidadAlmacenQueryDTO;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.ubigeos.DistritoQueryDTO;

@Data
@NoArgsConstructor
public class DireccionQueryDTO {

    private boolean almacenInterno;

    private AlmacenQueryDTO almacen;

    private EntidadAlmacenQueryDTO entidadAlmacen;

    private DistritoQueryDTO distrito;

    private String direccion;
}
