package pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.guia_remision.guia_remision_remitente;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.com.avivel.sistemas.guiaselectronicas.commons.files.FileNameAwareByteArrayResource;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResultObtenerCdrQueryDTO {

    private Integer estadoId;

    private String mensaje;

    private FileNameAwareByteArrayResource resource;
}
