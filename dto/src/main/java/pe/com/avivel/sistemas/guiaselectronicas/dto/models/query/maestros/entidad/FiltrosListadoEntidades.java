package pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.maestros.entidad;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class FiltrosListadoEntidades {

    @Min(0)
    @NotNull
    private Integer tipoDocumentoIdentidadId = 0;

    @NotNull
    private String nroDocumentoIdentidad = "";

    @NotNull
    private String razonSocial = "";
}
