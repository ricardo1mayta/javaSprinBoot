package pe.com.avivel.sistemas.guiaselectronicas.entities.security;

import lombok.*;
import org.hibernate.envers.Audited;
import pe.com.avivel.sistemas.guiaselectronicas.entities.maestros.Serie;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Audited
@Entity
@Access(AccessType.FIELD)
@Table(name = "seg_usuario_serie", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"usuario_id", "serie_id"} )})
@NoArgsConstructor
public class UsuarioSerie implements Serializable {

    private static final long serialVersionUID = 228219263752978749L;

    public UsuarioSerie(Usuario usuario, Serie serie){
        this.usuario = usuario;
        this.usuarioId = usuario.getId();
        this.serie = serie;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ToString.Exclude
    @Getter(AccessLevel.NONE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(name = "usuario_id", nullable = false, insertable = false, updatable = false)
    private Integer usuarioId;

    @ManyToOne
    @JoinColumn(name = "serie_id", nullable = false)
    private Serie serie;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_ingreso", nullable = false, updatable = false)
    private Date fechaIngreso;

    @PrePersist
    private void prePersist(){
        fechaIngreso = new Date();
    }
}
