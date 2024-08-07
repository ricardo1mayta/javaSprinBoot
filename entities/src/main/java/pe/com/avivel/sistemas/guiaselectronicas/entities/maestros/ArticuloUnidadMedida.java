package pe.com.avivel.sistemas.guiaselectronicas.entities.maestros;

import lombok.*;
import org.hibernate.envers.Audited;
import pe.com.avivel.sistemas.guiaselectronicas.entities.auxiliares.guia_remision.UnidadMedida;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Builder
@ToString
@Getter
@Setter
@Audited
@Entity
@Access(AccessType.FIELD)
@Table(name = "mae_articulo_unidad_medida", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"articulo_id", "unidad_medida_id"} )})
@NoArgsConstructor
@AllArgsConstructor
public class ArticuloUnidadMedida implements Serializable {

    private static final long serialVersionUID = -5221024533630349743L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "unidad_medida_id", nullable = false)
    private UnidadMedida unidadMedida;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_ingreso", nullable = false, updatable = false)
    private Date fechaIngreso;

    @ToString.Exclude
    @Getter(AccessLevel.NONE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "articulo_id", nullable = false)
    private Articulo articulo;

    @Column(name = "articulo_id", nullable = false, insertable = false, updatable = false)
    private Integer articuloId;

    @PrePersist
    private void prePersist(){
        fechaIngreso = new Date();
    }
}
