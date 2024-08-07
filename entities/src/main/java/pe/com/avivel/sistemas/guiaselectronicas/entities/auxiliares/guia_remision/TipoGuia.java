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
@Table(name = "gre_tipo_guia")
//@SQLDelete(sql = "UPDATE gre_tipo_guia SET deleted = true, fecha_eliminacion = CURRENT_TIMESTAMP  WHERE id=?")
//@Where(clause = "deleted = 0")
public class TipoGuia extends Auditable implements Serializable {

    private static final long serialVersionUID = 908326273326385948L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "codigo_sunat", length = 2, nullable = false, unique = true)
    private String codigoSunat;

    @Column(name = "abreviatura", length = 5, nullable = false)
    private String abreviatura;

    @Column(name = "descripcion", nullable = false)
    private String descripcion;
}
