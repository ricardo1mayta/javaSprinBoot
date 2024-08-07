package pe.com.avivel.sistemas.guiaselectronicas.entities.maestros.entidad;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.Audited;
import pe.com.avivel.sistemas.guiaselectronicas.entities.audit.Auditable;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Audited
@AuditOverride(forClass = Auditable.class)
@Entity
@Access(AccessType.FIELD)
@Table(name = "ent_entidad_correo", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"correo", "entidad_id"} )})
//@SQLDelete(sql = "UPDATE ent_entidad_correo SET deleted = true, fecha_eliminacion = CURRENT_TIMESTAMP  WHERE id=?")
//@Where(clause = "deleted = 0")
public class EntidadCorreo extends Auditable implements Serializable {

    private static final long serialVersionUID = -5043951882582786371L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "correo", nullable = false)
    private String correo;

    @ToString.Exclude
    @Getter(AccessLevel.NONE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "entidad_id", nullable = false)
    private Entidad entidad;

    @Column(name = "entidad_id", nullable = false, insertable = false, updatable = false)
    private Integer entidadId;
}
