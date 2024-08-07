package pe.com.avivel.sistemas.guiaselectronicas.entities.guia_remision.guia_remision_remitente.detalle;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.envers.Audited;
import pe.com.avivel.sistemas.guiaselectronicas.entities.auxiliares.TipoDocumentoIdentidad;
import pe.com.avivel.sistemas.guiaselectronicas.entities.guia_remision.guia_remision_remitente.DetalleGuiaRemisionRemitente;
import pe.com.avivel.sistemas.guiaselectronicas.entities.guia_remision.guia_remision_remitente.GuiaRemisionRemitente;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Audited
@Entity(name = "GuiaRemisionRemitenteConductorItem")
@Access(AccessType.FIELD)
@Table(name = "grr_guia_remision_remitente_conductor")
public class Conductor implements DetalleGuiaRemisionRemitente, Serializable {

    private static final long serialVersionUID = 2680107271356033439L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /**
     * El primer conductor tendra el rol de principal
     */
    @Column(name = "nro_orden", nullable = false)
    private Integer nroOrden;

    @ManyToOne
    @JoinColumn(name = "tipo_documento_identidad_id", nullable = false)
    private TipoDocumentoIdentidad tipoDocumentoIdentidad;

    @Column(name = "nro_documento_identidad", length = 15, nullable = false)
    private String nroDocumentoIdentidad;

    @Column(name = "apellidos", length = 250, nullable = false)
    private String apellidos;

    @Column(name = "nombres", length = 250, nullable = false)
    private String nombres;

    @Column(name = "nro_licencia_brevete", length = 10, nullable = false)
    private String nroLicenciaBrevete;

    @ToString.Exclude
    @Getter(AccessLevel.NONE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "guia_remision_remitente_id", nullable = false)
    private GuiaRemisionRemitente guiaRemisionRemitente;
}
