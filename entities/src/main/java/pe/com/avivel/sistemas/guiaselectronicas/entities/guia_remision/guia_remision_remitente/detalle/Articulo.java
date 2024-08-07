package pe.com.avivel.sistemas.guiaselectronicas.entities.guia_remision.guia_remision_remitente.detalle;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.envers.Audited;
import pe.com.avivel.sistemas.guiaselectronicas.entities.auxiliares.guia_remision.UnidadMedida;
import pe.com.avivel.sistemas.guiaselectronicas.entities.guia_remision.guia_remision_remitente.DetalleGuiaRemisionRemitente;
import pe.com.avivel.sistemas.guiaselectronicas.entities.guia_remision.guia_remision_remitente.GuiaRemisionRemitente;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Audited
@Entity(name = "GuiaRemisionRemitenteArticuloItem")
@Access(AccessType.FIELD)
@Table(name = "grr_guia_remision_remitente_articulo")
public class Articulo implements DetalleGuiaRemisionRemitente, Serializable {

    private static final long serialVersionUID = 912251003054643707L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nro_orden", nullable = false)
    private Integer nroOrden;

    @Column(name = "cantidad", columnDefinition = "numeric(22,10)", nullable = false)
    private Double cantidad;

    @ManyToOne
    @JoinColumn(name = "unidad_medida_id", nullable = false)
    private UnidadMedida unidadMedida;

    @Column(name = "codigo", length = 30, nullable = false)
    private String codigo;

    @Column(name = "descripcion", length = 500, nullable = false)
    private String descripcion;

    @Column(name = "peso", columnDefinition = "numeric(12,2)")
    private Double peso;

    @Column(name = "observacion", length = 500)
    private String observacion;

    @ToString.Exclude
    @Getter(AccessLevel.NONE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "guia_remision_remitente_id", nullable = false)
    private GuiaRemisionRemitente guiaRemisionRemitente;
}
