package pe.com.avivel.sistemas.guiaselectronicas.entities.auxiliares.guia_remision;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.Audited;
import pe.com.avivel.sistemas.guiaselectronicas.entities.audit.Auditable;

import javax.persistence.*;
import java.io.Serializable;

@ToString
@Getter
@Setter
@Audited
@AuditOverride(forClass = Auditable.class)
@Entity
@Table(name = "gre_unidad_medida")
//@SQLDelete(sql = "UPDATE gre_unidad_medida SET deleted = true, fecha_eliminacion = CURRENT_TIMESTAMP  WHERE id=?")
//@Where(clause = "deleted = 0")
public class UnidadMedida extends Auditable implements Serializable {

    private static final long serialVersionUID = 4683541832675163328L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "codigo_sunat", length = 3, nullable = false, unique = true)
    private String codigoSunat;

    @Column(name = "descripcion_sunat", length = 15, nullable = false)
    private String descripcionSunat;

    @Column(name = "abreviatura", length = 5, nullable = false)
    private String abreviatura;

    @Column(name = "descripcion", length = 100, nullable = false)
    private String descripcion;
}
