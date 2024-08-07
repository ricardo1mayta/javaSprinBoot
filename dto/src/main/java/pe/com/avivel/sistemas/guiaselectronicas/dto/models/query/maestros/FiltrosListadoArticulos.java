package pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.maestros;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FiltrosListadoArticulos {

    @NotNull
    private String codigo = "";

    @NotNull
    private String descripcion = "";
}
