package pe.com.avivel.sistemas.guiaselectronicas.dto.models.command.guia_remision.jventas;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ConductorDTO {

    private Integer nroOrden;
    private String tipoDocumentoIdentidad;
    private String numeroDocumentoIdentidad;
    private String nombres;
    private String apellidos;
    private String numeroLicencia;
}
