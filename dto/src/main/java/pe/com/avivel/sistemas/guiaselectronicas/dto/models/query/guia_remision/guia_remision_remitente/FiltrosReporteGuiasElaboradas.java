package pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.guia_remision.guia_remision_remitente;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.Date;

@Data
@NoArgsConstructor
public class FiltrosReporteGuiasElaboradas {

    @NotNull
    private String serie = "";

    @PositiveOrZero
    private Integer motivoTrasladoId;

    @NotNull
    private Date fechaDesde;

    @NotNull
    private Date fechaHasta;

    @PositiveOrZero
    private Integer estadoId = 0;

    @PositiveOrZero
    private Integer destinatarioTipoDocumentoIdentidadId = 0;

    @NotNull
    private String destinatarioNroDocumentoIdentidad = "";

    @NotNull
    private String destinatarioRazonSocial = "";
}
