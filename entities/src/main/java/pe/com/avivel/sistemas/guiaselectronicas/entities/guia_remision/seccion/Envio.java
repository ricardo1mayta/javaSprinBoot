package pe.com.avivel.sistemas.guiaselectronicas.entities.guia_remision.seccion;

import lombok.*;
import pe.com.avivel.sistemas.guiaselectronicas.entities.auxiliares.guia_remision.UnidadMedidaPesoBruto;
import pe.com.avivel.sistemas.guiaselectronicas.entities.auxiliares.guia_remision.guia_remision_remitente.ModalidadTraslado;
import pe.com.avivel.sistemas.guiaselectronicas.entities.auxiliares.guia_remision.guia_remision_remitente.MotivoTraslado;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Embeddable
public class Envio implements Serializable {

    private static final long serialVersionUID = -4379398131669698437L;

    @Column(name = "identificador_traslado", length = 11, nullable = false)
    private String identificadorTraslado = "SUNAT_Envio";

    @ManyToOne
    @JoinColumn(name = "motivo_traslado_id", nullable = false)
    private MotivoTraslado motivoTraslado;

    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_inicio_traslado")
    private Date fechaInicioTraslado;

    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_entrega_bienes_transportista")
    private Date fechaEntregaBienesTransportista;

    @Column(name = "descripcion_motivo_traslado", length = 100, nullable = false)
    private String descripcionMotivoTraslado;

    @ManyToOne
    @JoinColumn(name = "unidad_medida_peso_bruto_id", nullable = false)
    private UnidadMedidaPesoBruto unidadMedidaPesoBruto;

    @Column(name = "peso_bruto", columnDefinition = "numeric(15,3)", nullable = false)
    private Double pesoBruto;

    @ManyToOne
    @JoinColumn(name = "modalidad_traslado_id", nullable = false)
    private ModalidadTraslado modalidadTraslado;

    @Column(name = "is_traslado_vehiculos_m1_l", nullable = false)
    private boolean trasladoVehiculosM1L;
}
