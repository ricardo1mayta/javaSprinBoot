package pe.com.avivel.sistemas.guiaselectronicas.entities.security;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.Audited;
import pe.com.avivel.sistemas.guiaselectronicas.entities.audit.Auditable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@ToString
@Getter
@Setter
@Audited
@AuditOverride(forClass = Auditable.class)
@Entity
@Table(name = "seg_sesion")
//@SQLDelete(sql = "UPDATE seg_sesion SET deleted = true, fecha_eliminacion = CURRENT_TIMESTAMP  WHERE id=?")
//@Where(clause = "deleted = 0")
public class Sesion extends Auditable implements Serializable {

    private static final long serialVersionUID = -3327121986361796996L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "cliente", nullable = false)
    private String cliente;

    @Column(name = "ip", nullable = false)
    private String ip;

    @Column(name = "pais", nullable = false)
    private String pais;

    @Column(name = "token", length = 72, nullable = false)
    private String token;

    @Column(name = "token_creacion", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date tokenCreacion;

    @Column(name = "token_expiracion", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date tokenExpiracion;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;
}
