package pe.com.avivel.sistemas.guiaselectronicas.entities.maestros.vehiculo;

import lombok.Getter;
import lombok.Setter;
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
@Table(name = "veh_categoria")
//@SQLDelete(sql = "UPDATE veh_categoria SET deleted = true, fecha_eliminacion = CURRENT_TIMESTAMP  WHERE id=?")
//@Where(clause = "deleted = 0")
public class Categoria extends Auditable implements Serializable {

    private static final long serialVersionUID = 515425889080565721L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "codigo", length = 3, nullable = false)
    private String codigo;

    @Column(name = "descripcion", nullable = false)
    private String descripcion;
}
