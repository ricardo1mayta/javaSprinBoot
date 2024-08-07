package pe.com.avivel.sistemas.guiaselectronicas.dto.models.command.guia_remision.jventas;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EmpresaTransportistaDTO {

    private String ruc;
    private String razonSocial;
    private String direccion;
    private String distrito;
}
