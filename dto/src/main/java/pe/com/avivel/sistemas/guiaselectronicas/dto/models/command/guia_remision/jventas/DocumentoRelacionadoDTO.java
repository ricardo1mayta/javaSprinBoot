package pe.com.avivel.sistemas.guiaselectronicas.dto.models.command.guia_remision.jventas;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DocumentoRelacionadoDTO {
    private String tipoDocumentoCodigo;
    private String numeroSerieDocumento;
    private String rucEmisor;

}
