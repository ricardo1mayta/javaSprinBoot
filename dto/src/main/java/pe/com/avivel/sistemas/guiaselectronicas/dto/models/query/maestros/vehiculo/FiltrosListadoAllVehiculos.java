package pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.maestros.vehiculo;

import lombok.Data;
import lombok.NoArgsConstructor;
import pe.com.avivel.sistemas.guiaselectronicas.entities.util.EstadoGeneral;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class FiltrosListadoAllVehiculos {

    @NotNull
    private String nroPlaca = "";

    @NotNull
    private String descripcion = "";

    @NotNull
    private EstadoGeneral estado = EstadoGeneral.T;
}
