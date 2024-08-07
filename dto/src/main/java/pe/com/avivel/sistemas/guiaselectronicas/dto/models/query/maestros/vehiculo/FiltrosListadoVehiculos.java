package pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.maestros.vehiculo;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class FiltrosListadoVehiculos {

    @NotNull
    private String nroPlaca = "";

    @NotNull
    private String descripcion = "";

}
