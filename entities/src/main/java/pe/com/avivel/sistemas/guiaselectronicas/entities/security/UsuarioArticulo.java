package pe.com.avivel.sistemas.guiaselectronicas.entities.security;

import lombok.*;
import org.hibernate.envers.Audited;
import pe.com.avivel.sistemas.guiaselectronicas.entities.maestros.Articulo;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Audited
@Entity
@Access(AccessType.FIELD)
@Table(name = "seg_usuario_articulo", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"usuario_id", "articulo_id"} )})
@NoArgsConstructor
public class UsuarioArticulo implements Serializable {

    private static final long serialVersionUID = 7034530928183261014L;

    public UsuarioArticulo(Usuario usuario, Articulo articulo){
        this.usuario = usuario;
        this.usuarioId = usuario.getId();
        this.articulo = articulo;
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
    @JoinColumn(name = "articulo_id", nullable = false)
    private Articulo articulo;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_ingreso", nullable = false, updatable = false)
    private Date fechaIngreso;

    @PrePersist
    private void prePersist(){
        fechaIngreso = new Date();
    }
}
