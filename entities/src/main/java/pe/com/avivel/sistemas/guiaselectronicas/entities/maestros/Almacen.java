package pe.com.avivel.sistemas.guiaselectronicas.entities.maestros;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.Audited;
import pe.com.avivel.sistemas.guiaselectronicas.entities.audit.Auditable;
import pe.com.avivel.sistemas.guiaselectronicas.entities.ubigeos.Distrito;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Audited
@AuditOverride(forClass = Auditable.class)
@Entity
@Table(name = "mae_almacen")
public class Almacen extends Auditable implements Serializable {

    private static final long serialVersionUID = -8447760711049841361L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "codigo_sunat", length = 6, nullable = false, unique = true)
    private String codigoSunat;

    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "distrito_id", nullable = false)
    private Distrito distrito;

    @Column(name = "direccion", length = 500, nullable = false)
    private String direccion;

    @Column(name = "observacion", length = 500, nullable = false)
    private String observacion;
}
