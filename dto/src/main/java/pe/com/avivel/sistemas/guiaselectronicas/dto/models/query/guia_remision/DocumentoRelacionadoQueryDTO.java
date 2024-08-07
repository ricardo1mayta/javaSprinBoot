package pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.guia_remision;

import lombok.Data;
import lombok.NoArgsConstructor;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.auxiliar.TipoDocumentoQueryDTO;

@Data
@NoArgsConstructor
public class DocumentoRelacionadoQueryDTO {

    private Integer id;

    private TipoDocumentoQueryDTO tipoDocumento;

    private String nroDocumento;

    private boolean emitidoInterno;

    private String rucEmisor;
}
