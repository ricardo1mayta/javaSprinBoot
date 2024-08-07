package pe.com.avivel.sistemas.guiaselectronicas.dto.models.command.guia_remision.jventas;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PuntoDeLlegadaDTO {

    private String nombre;
    private String direccion;
    private String distrito;
}
