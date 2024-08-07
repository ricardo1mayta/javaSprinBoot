package pe.com.avivel.sistemas.guiaselectronicas.entities.auxiliares.guia_remision;

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
@Table(name = "gre_estado_guia")
//@SQLDelete(sql = "UPDATE gre_estado_guia SET deleted = true, fecha_eliminacion = CURRENT_TIMESTAMP  WHERE id=?")
//@Where(clause = "deleted = 0")
public class EstadoGuia extends Auditable implements Serializable {

    private static final long serialVersionUID = -3471539207525449018L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "abreviatura", length = 5, nullable = false)
    private String abreviatura;

    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @Column(name = "observacion", length = 500)
    private String observacion;
}
