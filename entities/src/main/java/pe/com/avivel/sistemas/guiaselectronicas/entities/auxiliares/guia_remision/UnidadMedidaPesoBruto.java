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
@Table(name = "gre_unidad_medida_peso_bruto")
//@SQLDelete(sql = "UPDATE gre_unidad_medida_peso_bruto SET deleted = true, fecha_eliminacion = CURRENT_TIMESTAMP  WHERE id=?")
//@Where(clause = "deleted = 0")
public class UnidadMedidaPesoBruto extends Auditable implements Serializable {

    private static final long serialVersionUID = 6695749471993378733L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "codigo_sunat", length = 3, nullable = false, unique = true)
    private String codigoSunat;

    @Column(name = "abreviatura", length = 5, nullable = false)
    private String abreviatura;

    @Column(name = "descripcion", length = 50, nullable = false)
    private String descripcion;
}
