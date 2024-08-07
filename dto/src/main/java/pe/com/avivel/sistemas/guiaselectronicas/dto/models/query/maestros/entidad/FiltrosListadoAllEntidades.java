package pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.maestros.entidad;

import lombok.Data;
import lombok.NoArgsConstructor;
import pe.com.avivel.sistemas.guiaselectronicas.entities.util.EstadoGeneral;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class FiltrosListadoAllEntidades {

    @Min(0)
    @NotNull
    private Integer tipoDocumentoIdentidadId = 0;

    @NotNull
    private String nroDocumentoIdentidad = "";

    @NotNull
    private String razonSocial = "";

    @NotNull
    private EstadoGeneral estado = EstadoGeneral.T;
}
