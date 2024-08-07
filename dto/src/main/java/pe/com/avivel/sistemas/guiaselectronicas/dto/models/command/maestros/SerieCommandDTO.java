package pe.com.avivel.sistemas.guiaselectronicas.dto.models.command.maestros;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
public class SerieCommandDTO {

    public static final String CODIGO_PATTERN = "^T[0-9]+$";

    public static final String CODIGO_MESSAGE = "La serie solo debe de estar compueta por la letra T y seguido de una secuencia de números.";

    @Pattern(regexp = CODIGO_PATTERN, message = CODIGO_MESSAGE )
    @NotBlank
    private String codigo;

    @NotNull
    private String abreviatura = "";

    @NotBlank
    private String descripcion = "";

    @NotNull
    private boolean activo = true;
}
