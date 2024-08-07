package pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.maestros;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import pe.com.avivel.sistemas.guiaselectronicas.entities.util.EstadoGeneral;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class FiltrosListadoAllConductores {

    @Min(0)
    @NotNull
    private Integer tipoDocumentoIdentidadId = 0;

    @NotNull
    private String nroDocumentoIdentidad = "";

    @NonNull
    private String nombre = "";

    @NotNull
    private EstadoGeneral estado = EstadoGeneral.T;
}
