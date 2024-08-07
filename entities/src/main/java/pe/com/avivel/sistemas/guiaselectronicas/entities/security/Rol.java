package pe.com.avivel.sistemas.guiaselectronicas.entities.security;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;
import pe.com.avivel.sistemas.guiaselectronicas.entities.audit.Auditable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Audited
@AuditOverride(forClass = Auditable.class)
@Entity
@Table(name = "seg_rol")
//@SQLDelete(sql = "UPDATE seg_rol SET deleted = true, fecha_eliminacion = CURRENT_TIMESTAMP  WHERE id=?")
//@Where(clause = "deleted = 0")
public class Rol extends Auditable implements Serializable {

    private static final long serialVersionUID = 1710752755317497993L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nombre", nullable = false, unique = true)
    private String nombre;

    @Column(name = "tipo", columnDefinition = "ENUM('M','P')" , nullable = false)
    @Enumerated(EnumType.STRING)
    private RolTipo tipo;

    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinTable(
            name = "seg_rol_menu",
            joinColumns = @JoinColumn(name = "rol_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "menu_id", referencedColumnName = "id"),
            uniqueConstraints = {@UniqueConstraint(columnNames = {"rol_id","menu_id"})}
    )
    private List<Menu> menus;
}
