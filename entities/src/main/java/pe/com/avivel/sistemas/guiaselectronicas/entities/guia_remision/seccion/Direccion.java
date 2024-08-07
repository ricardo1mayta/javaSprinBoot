package pe.com.avivel.sistemas.guiaselectronicas.entities.guia_remision.seccion;

import lombok.*;
import pe.com.avivel.sistemas.guiaselectronicas.entities.maestros.Almacen;
import pe.com.avivel.sistemas.guiaselectronicas.entities.maestros.entidad.EntidadAlmacen;
import pe.com.avivel.sistemas.guiaselectronicas.entities.ubigeos.Distrito;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Embeddable
public class Direccion implements Serializable {

    private static final long serialVersionUID = 7684709833405665500L;

    @Column(name = "is_almacen_interno")
    private boolean almacenInterno;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "almacen_id")
    private Almacen almacen;

    @ManyToOne
    @JoinColumn(name = "entidad_almacen_id")
    private EntidadAlmacen entidadAlmacen;

    @ManyToOne
    @JoinColumn(name = "distrito_id")
    private Distrito distrito;

    @Column(name = "direccion", length = 500)
    private String direccion;
}
