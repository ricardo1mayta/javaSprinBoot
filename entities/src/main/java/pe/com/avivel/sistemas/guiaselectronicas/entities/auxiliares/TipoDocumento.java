package pe.com.avivel.sistemas.guiaselectronicas.entities.auxiliares;

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
@Table(name = "cfg_tipo_documento")
//@SQLDelete(sql = "UPDATE cfg_tipo_documento SET deleted = true, fecha_eliminacion = CURRENT_TIMESTAMP  WHERE id=?")
//@Where(clause = "deleted = 0")
public class TipoDocumento extends Auditable implements Serializable {

    private static final long serialVersionUID = -2433229451082666176L;

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
