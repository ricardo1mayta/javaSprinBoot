package pe.com.avivel.sistemas.guiaselectronicas.entities.guia_remision.guia_remision_remitente.detalle;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.envers.Audited;
import pe.com.avivel.sistemas.guiaselectronicas.entities.guia_remision.guia_remision_remitente.DetalleGuiaRemisionRemitente;
import pe.com.avivel.sistemas.guiaselectronicas.entities.guia_remision.guia_remision_remitente.GuiaRemisionRemitente;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Audited
@Entity(name = "GuiaRemisionRemitenteVehiculoItem")
@Access(AccessType.FIELD)
@Table(name = "grr_guia_remision_remitente_vehiculo")
public class Vehiculo implements DetalleGuiaRemisionRemitente, Serializable {

    private static final long serialVersionUID = 3732982265158994155L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /**
     * El primer vehiculo tendra el rol de principal
     */
    @Column(name = "nro_orden", nullable = false)
    private Integer nroOrden;

    @Column(name = "numero_placa", length = 8, nullable = false)
    private String numeroPlaca;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "entidad_autorizadora", length = 50)
    private String entidadAutorizadora;

    @Column(name = "nro_autorizacion", length = 50)
    private String nroAutorizacion;

    @ToString.Exclude
    @Getter(AccessLevel.NONE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "guia_remision_remitente_id", nullable = false)
    private GuiaRemisionRemitente guiaRemisionRemitente;
}
