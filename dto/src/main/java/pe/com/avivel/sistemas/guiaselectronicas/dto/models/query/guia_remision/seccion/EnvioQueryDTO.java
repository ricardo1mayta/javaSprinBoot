package pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.guia_remision.seccion;

import lombok.Data;
import lombok.NoArgsConstructor;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.auxiliar.guia_remision.UnidadMedidaPesoBrutoQueryDTO;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.auxiliar.guia_remision.guia_remision_remitente.ModalidadTrasladoQueryDTO;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.auxiliar.guia_remision.guia_remision_remitente.MotivoTrasladoQueryDTO;

import java.util.Date;

@Data
@NoArgsConstructor
public class EnvioQueryDTO {

    private MotivoTrasladoQueryDTO motivoTraslado;

    private Date fechaInicioTraslado;

    private Date fechaEntregaBienesTransportista;

    private String descripcionMotivoTraslado;

    private UnidadMedidaPesoBrutoQueryDTO unidadMedidaPesoBruto;

    private Double pesoBruto;

    private ModalidadTrasladoQueryDTO modalidadTraslado;

    private boolean trasladoVehiculosM1L;
}
