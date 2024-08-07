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
//@Access(AccessType.FIELD)
@Table(name = "ubi_distrito")
//@SQLDelete(sql = "UPDATE ubi_distrito SET deleted = true, fecha_eliminacion = CURRENT_TIMESTAMP  WHERE id=?")
//@Where(clause = "deleted = 0")
public class Distrito extends Auditable implements Serializable {

    private static final long serialVersionUID = -7737611553904809399L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "codigo_ubigeo", length = 6, nullable = false, unique = true)
    private String codigoUbigeo;

    @Column(name = "abreviatura", length = 5, nullable = false)
    private String abreviatura;

    @Column(name = "descripcion", length = 30, nullable = false)
    private String descripcion;

//    @ToString.Exclude
//    @Getter(AccessLevel.NONE)
    @ManyToOne
    @JoinColumn(name = "provincia_id", nullable = false)
    private Provincia provincia;

//    @Column(name = "provincia_id", nullable = false, insertable = false, updatable = false)
//    private Integer provinciaId;
//
//    @NotAudited
//    @Formula("(select prov.departamento_id from ubi_provincia prov where prov.id = provincia_id)")
//    private Integer departamentoId;
}
