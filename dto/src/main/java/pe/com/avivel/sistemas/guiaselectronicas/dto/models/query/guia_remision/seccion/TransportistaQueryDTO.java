package pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.guia_remision.seccion;

import lombok.Data;
import lombok.NoArgsConstructor;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.auxiliar.TipoDocumentoIdentidadQueryDTO;

@Data
@NoArgsConstructor
public class TransportistaQueryDTO {

    private TipoDocumentoIdentidadQueryDTO tipoDocumentoIdentidad;

    private String nroDocumentoIdentidad;

    private String razonSocial;

    private String nroRegistroMTC;
}
