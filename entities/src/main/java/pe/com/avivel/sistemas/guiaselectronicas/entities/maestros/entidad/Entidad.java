package pe.com.avivel.sistemas.guiaselectronicas.entities.maestros.entidad;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.Audited;
import pe.com.avivel.sistemas.guiaselectronicas.entities.audit.Auditable;
import pe.com.avivel.sistemas.guiaselectronicas.entities.auxiliares.TipoDocumentoIdentidad;
import pe.com.avivel.sistemas.guiaselectronicas.entities.ubigeos.Distrito;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Audited
@AuditOverride(forClass = Auditable.class)
@Entity
@Table(name = "ent_entidad", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"tipo_documento_identidad_id", "nro_documento_identidad"} )})
@NoArgsConstructor
//@SQLDelete(sql = "UPDATE ent_entidad SET deleted = true, fecha_eliminacion = CURRENT_TIMESTAMP  WHERE id=?")
//@Where(clause = "deleted = 0")
public class Entidad extends Auditable implements Serializable {

    private static final long serialVersionUID = -1259255073236180624L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "tipo_documento_identidad_id", nullable = false)
    private TipoDocumentoIdentidad tipoDocumentoIdentidad;

    @Column(name = "nro_documento_identidad", length = 15, nullable = false)
    private String nroDocumentoIdentidad;

    @Column(name = "razon_social", length = 250, nullable = false)
    private String razonSocial;

    @Column(name = "apellidos", length = 250, nullable = false)
    private String apellidos;

    @Column(name = "nombres", length = 250, nullable = false)
    private String nombres;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "distrito_id")
    private Distrito distrito;

    @Column(name = "direccion", length = 500, nullable = false)
    private String direccion;

    @OneToMany(mappedBy = "entidad")
    private List<EntidadCorreo> correos;

    @OneToMany(mappedBy = "entidad")
    private List<EntidadAlmacen> almacenes;
}
