package pe.com.avivel.sistemas.guiaselectronicas.entities.security;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;
import pe.com.avivel.sistemas.guiaselectronicas.entities.audit.Auditable;
import pe.com.avivel.sistemas.guiaselectronicas.entities.guia_remision.seccion.Transportista;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Audited
@AuditOverride(forClass = Auditable.class)
@Entity
@Table(name = "seg_usuario")
@NoArgsConstructor
//@SQLDelete(sql = "UPDATE seg_usuario SET deleted = true, fecha_eliminacion = CURRENT_TIMESTAMP  WHERE id=?")
//@Where(clause = "deleted = 0")
public class Usuario extends Auditable implements Serializable {

    private static final long serialVersionUID = -2534974495464013698L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "username", length = 100, nullable = false, unique = true)
    private String username;

    @Column(name = "password", length = 100, nullable = false)
    private String password;

    @Column(name = "apellidos", length = 250, nullable = false)
    private String apellidos;

    @Column(name = "nombres", length = 250, nullable = false)
    private String nombres;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @ColumnDefault("0")
    @Column(name = "is_usuario_externo", nullable = false)
    private boolean usuarioExterno;

    @Embedded
    @AssociationOverrides({
            @AssociationOverride(name = "tipoDocumentoIdentidad", joinColumns = @JoinColumn(name = "dtr_tipo_documento_identidad_id"))
    })
    @AttributeOverrides({
            @AttributeOverride(name = "nroDocumentoIdentidad", column = @Column(name = "dtr_nro_documento_identidad", length = 15)),
            @AttributeOverride(name = "razonSocial", column = @Column(name = "dtr_razon_social", length = 250)),
            @AttributeOverride(name = "nroRegistroMTC", column = @Column(name = "dtr_nro_registro_MTC", length = 20))
    })
    private Transportista datosTransportista;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ultimo_login")
    private Date ultimoLogin;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE, mappedBy = "usuario")
    private List<UsuarioSerie> series;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE, mappedBy = "usuario")
    private List<UsuarioEntidad> entidades;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE, mappedBy = "usuario")
    private List<UsuarioArticulo> articulos;

    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(
            name = "seg_usuario_rol",
            joinColumns = @JoinColumn(name = "usuario_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "rol_id", referencedColumnName = "id"),
            uniqueConstraints = {@UniqueConstraint(columnNames = {"usuario_id","rol_id"})}
    )
    private List<Rol> roles;
}
