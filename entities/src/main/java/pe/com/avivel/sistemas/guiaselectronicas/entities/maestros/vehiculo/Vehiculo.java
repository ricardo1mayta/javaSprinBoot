package pe.com.avivel.sistemas.guiaselectronicas.entities.maestros.vehiculo;

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
@Table(name = "veh_vehiculo")
//@SQLDelete(sql = "UPDATE veh_vehiculo SET deleted = true, fecha_eliminacion = CURRENT_TIMESTAMP  WHERE id=?")
//@Where(clause = "deleted = 0")
public class Vehiculo extends Auditable implements Serializable {

    private static final long serialVersionUID = 7015643943098385065L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @Column(name = "placa_principal", length = 8, nullable = false, unique = true)
    private String placaPrincipal;

    @Column(name = "placa_secundaria", length = 8)
    private String placaSecundaria;

    @ManyToOne
    @JoinColumn(name = "marca_id")
    private Marca marca;

    @Column(name = "modelo", length = 25, nullable = false)
    private String modelo;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    @Column(name = "entidad_autorizadora", length = 50, nullable = false)
    private String entidadAutorizadora;

    @Column(name = "nro_autorizacion", length = 50, nullable = false)
    private String nroAutorizacion;
}
