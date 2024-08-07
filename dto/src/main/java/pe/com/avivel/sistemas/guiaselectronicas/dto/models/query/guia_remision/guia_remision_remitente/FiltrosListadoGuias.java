package pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.guia_remision.guia_remision_remitente;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@NoArgsConstructor
public class FiltrosListadoGuias {

    @NotNull
    private String serie;

    @NotNull
    private String numero = "";

    @Min(0)
    @NotNull
    private Integer motivoTrasladoId = 0;

    private Date fechaEmision;

    @NotNull
    private String nroDocumentoIdentidad = "";

    @NotNull
    private String razonSocial = "";
}
