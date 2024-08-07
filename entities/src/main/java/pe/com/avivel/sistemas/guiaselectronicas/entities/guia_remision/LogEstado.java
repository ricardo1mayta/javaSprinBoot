package pe.com.avivel.sistemas.guiaselectronicas.entities.guia_remision;

import lombok.*;
import org.hibernate.envers.Audited;
import pe.com.avivel.sistemas.guiaselectronicas.entities.auxiliares.guia_remision.EstadoGuia;
import pe.com.avivel.sistemas.guiaselectronicas.entities.security.Usuario;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Builder
@Getter
@Setter
@Audited
@Entity
@Access(AccessType.FIELD)
@Table(name = "gre_guia_remision_log")
@AllArgsConstructor
@NoArgsConstructor
public class LogEstado implements Serializable {

    private static final long serialVersionUID = 8303891820195344809L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha", nullable = false)
    private Date fecha;

    @ManyToOne
    @JoinColumn(name = "estado_id", nullable = false)
    private EstadoGuia estado;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ToString.Exclude
    @Getter(AccessLevel.NONE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "guia_remision_id", nullable = false)
    private GuiaRemision guiaRemision;
}
