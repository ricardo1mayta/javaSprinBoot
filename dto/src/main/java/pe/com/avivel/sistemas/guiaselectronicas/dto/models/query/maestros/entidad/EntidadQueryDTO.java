package pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.maestros.entidad;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.auxiliar.TipoDocumentoIdentidadQueryDTO;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.ubigeos.DistritoQueryDTO;

import java.util.Date;

@Data
@NoArgsConstructor
public class EntidadQueryDTO {

    private Integer id;

    private TipoDocumentoIdentidadQueryDTO tipoDocumentoIdentidad;

    private String nroDocumentoIdentidad;

    private String razonSocial;

    private String apellidos;

    private String nombres;

    private DistritoQueryDTO distrito;

    private String direccion;

    @JsonProperty("fechaRegistro")
    private Date creationDate;

    private boolean activo;
}
