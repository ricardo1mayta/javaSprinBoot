package pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.guia_remision.seccion;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.auxiliar.TipoDocumentoIdentidadQueryDTO;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.ubigeos.DistritoQueryDTO;

@Slf4j
@Data
@NoArgsConstructor
public class DestinatarioQueryDTO {

    private TipoDocumentoIdentidadQueryDTO tipoDocumentoIdentidad;

    private String nroDocumentoIdentidad;

    private String razonSocial;

    private String direccion;

    private DistritoQueryDTO distrito;
}
