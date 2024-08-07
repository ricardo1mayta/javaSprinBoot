package pe.com.avivel.sistemas.guiaselectronicas.dto.models.command.maestros;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ArticuloCommandDTO {

    @Size(max = 30)
    @NotNull
    private String codigo;

    @Size(max = 500)
    @NotNull
    private String descripcion;

    @NotNull
    private boolean activo = true;
}
