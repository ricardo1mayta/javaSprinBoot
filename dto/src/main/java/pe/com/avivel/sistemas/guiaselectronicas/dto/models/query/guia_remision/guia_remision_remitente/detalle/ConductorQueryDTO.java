package pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.guia_remision.guia_remision_remitente.detalle;

import lombok.Data;
import lombok.NoArgsConstructor;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.auxiliar.TipoDocumentoIdentidadQueryDTO;

@Data
@NoArgsConstructor
public class ConductorQueryDTO {

    private Integer id;

    private Integer nroOrden;

    private TipoDocumentoIdentidadQueryDTO tipoDocumentoIdentidad;

    private String nroDocumentoIdentidad;

    private String apellidos;

    private String nombres;

    private String nroLicenciaBrevete;
}
