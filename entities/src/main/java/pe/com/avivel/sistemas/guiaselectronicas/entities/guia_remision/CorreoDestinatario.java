package pe.com.avivel.sistemas.guiaselectronicas.entities.guia_remision;

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
@Entity
@Access(AccessType.FIELD)
@Table(name = "gre_guia_remision_correo_destinatario")
public class CorreoDestinatario implements DetalleGuiaRemisionRemitente, Serializable {

    private static final long serialVersionUID = -5779009942759581863L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "correo", nullable = false)
    private String correo;

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
