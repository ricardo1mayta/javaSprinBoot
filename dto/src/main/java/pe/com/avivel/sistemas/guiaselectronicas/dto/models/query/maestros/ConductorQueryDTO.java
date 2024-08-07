package pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.maestros;

import lombok.Data;
import lombok.NoArgsConstructor;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.auxiliar.TipoDocumentoIdentidadQueryDTO;

import java.util.Objects;

@Data
@NoArgsConstructor
public class ConductorQueryDTO {

    private Integer id;

    private TipoDocumentoIdentidadQueryDTO tipoDocumentoIdentidad;

    private String nroDocumentoIdentidad;

    private String apellidos;

    private String nombres;

    private String nroLicenciaBrevete;

    private boolean activo;

    public String getNombreCompleto(){
        return Objects.requireNonNullElse(this.apellidos, "") + ", " +
                Objects.requireNonNullElse(this.nombres, "");
    }
}
