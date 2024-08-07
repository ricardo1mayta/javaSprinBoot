package pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.maestros.entidad;

import lombok.Data;
import lombok.NoArgsConstructor;
import pe.com.avivel.sistemas.guiaselectronicas.entities.util.EstadoGeneral;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class FiltrosListadoAllEntidadAlmacenes {

    @NotNull
    private Integer entidadId;

    @NotNull
    private String descripcion = "";

    @NotNull
    private EstadoGeneral estado = EstadoGeneral.T;
}
