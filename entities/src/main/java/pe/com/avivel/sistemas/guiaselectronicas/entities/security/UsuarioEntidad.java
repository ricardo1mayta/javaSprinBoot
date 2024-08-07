package pe.com.avivel.sistemas.guiaselectronicas.entities.security;

import lombok.*;
import org.hibernate.envers.Audited;
import pe.com.avivel.sistemas.guiaselectronicas.entities.maestros.entidad.Entidad;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Audited
@Entity
@Access(AccessType.FIELD)
@Table(name = "seg_usuario_entidad", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"usuario_id", "entidad_id"} )})
@NoArgsConstructor
public class UsuarioEntidad implements Serializable {

    private static final long serialVersionUID = -3079334660357551569L;

    public UsuarioEntidad(Usuario usuario, Entidad entidad){
        this.usuario = usuario;
        this.usuarioId = usuario.getId();
        this.entidad = entidad;
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
    @JoinColumn(name = "entidad_id", nullable = false)
    private Entidad entidad;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_ingreso", nullable = false, updatable = false)
    private Date fechaIngreso;

    @PrePersist
    private void prePersist(){
        fechaIngreso = new Date();
    }
}
