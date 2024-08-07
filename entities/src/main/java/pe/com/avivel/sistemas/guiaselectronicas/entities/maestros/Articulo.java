package pe.com.avivel.sistemas.guiaselectronicas.entities.maestros;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.Audited;
import pe.com.avivel.sistemas.guiaselectronicas.entities.audit.Auditable;
import pe.com.avivel.sistemas.guiaselectronicas.entities.auxiliares.guia_remision.UnidadMedida;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Audited
@AuditOverride(forClass = Auditable.class)
@Entity
@Table(name = "mae_articulo")
@NoArgsConstructor
//@SQLDelete(sql = "UPDATE mae_articulo SET deleted = true, fecha_eliminacion = CURRENT_TIMESTAMP  WHERE id=?")
//@Where(clause = "deleted = 0")
public class Articulo extends Auditable implements Serializable {

    private static final long serialVersionUID = -8943067553822204936L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "codigo", length = 30, nullable = false, unique = true)
    private String codigo;

    @Column(name = "descripcion", length = 500, nullable = false)
    private String descripcion;

    @Getter(AccessLevel.NONE)
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "articulo")
    private List<ArticuloUnidadMedida> unidadMedidas;

    @SuppressWarnings("unused")
    public List<UnidadMedida> getUnidadMedidas(){
        if(unidadMedidas == null){
            return Collections.emptyList();
        }

        return unidadMedidas
                .stream()
                .map(ArticuloUnidadMedida::getUnidadMedida)
                .collect(Collectors.toList());
    }
}
