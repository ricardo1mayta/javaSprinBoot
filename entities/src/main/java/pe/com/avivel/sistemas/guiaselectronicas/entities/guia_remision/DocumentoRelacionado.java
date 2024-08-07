package pe.com.avivel.sistemas.guiaselectronicas.entities.guia_remision;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.envers.Audited;
import pe.com.avivel.sistemas.guiaselectronicas.entities.auxiliares.TipoDocumento;
import pe.com.avivel.sistemas.guiaselectronicas.entities.guia_remision.guia_remision_remitente.DetalleGuiaRemisionRemitente;
import pe.com.avivel.sistemas.guiaselectronicas.entities.guia_remision.guia_remision_remitente.GuiaRemisionRemitente;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Audited
@Entity
@Access(AccessType.FIELD)
@Table(name = "gre_guia_remision_documento_relacionado")
public class DocumentoRelacionado implements DetalleGuiaRemisionRemitente, Serializable {

    private static final long serialVersionUID = 2442811114387883178L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "tipo_documento_id", nullable = false)
    private TipoDocumento tipoDocumento;

    @Column(name = "nro_documento", length = 100, nullable = false)
    private String nroDocumento;

    @Column(name = "is_emitido_interno", nullable = false)
    private boolean emitidoInterno;

    @Column(name = "ruc_emisor", length = 11, nullable = false)
    private String rucEmisor;

    @ToString.Exclude
    @Getter(AccessLevel.NONE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "guia_remision_id", nullable = false)
    private GuiaRemision guiaRemision;

    @Override
    public void setGuiaRemisionRemitente(GuiaRemisionRemitente guiaRemisionRemitente) {
        this.setGuiaRemision(guiaRemisionRemitente);
    }
}
