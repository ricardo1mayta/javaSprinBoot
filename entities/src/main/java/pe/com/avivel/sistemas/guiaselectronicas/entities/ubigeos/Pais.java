package pe.com.avivel.sistemas.guiaselectronicas.entities.ubigeos;

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
@Table(name = "ubi_pais")
//@SQLDelete(sql = "UPDATE ubi_pais SET deleted = true, fecha_eliminacion = CURRENT_TIMESTAMP  WHERE id=?")
//@Where(clause = "deleted = 0")
public class Pais extends Auditable implements Serializable {

    private static final long serialVersionUID = 898718044599345522L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "codigo", length = 2, nullable = false, unique = true)
    private String codigo;

    @Column(name = "abreviatura", length = 5, nullable = false)
    private String abreviatura;

    @Column(name = "descripcion", length = 30, nullable = false)
    private String descripcion;
}
