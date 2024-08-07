package pe.com.avivel.sistemas.guiaselectronicas.entities.guia_remision.seccion;

import lombok.*;
import pe.com.avivel.sistemas.guiaselectronicas.entities.auxiliares.TipoDocumentoIdentidad;
import pe.com.avivel.sistemas.guiaselectronicas.entities.ubigeos.Distrito;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Embeddable
public class Destinatario implements Serializable {

    private static final long serialVersionUID = -1108573696194226519L;

    @ManyToOne
    @JoinColumn(name = "tipo_documento_identidad_id", nullable = false)
    private TipoDocumentoIdentidad tipoDocumentoIdentidad;

    @Column(name = "nro_documento_identidad", length = 15, nullable = false)
    private String nroDocumentoIdentidad;

    @Column(name = "razon_social", length = 250, nullable = false)
    private String razonSocial;

    @Column(name = "direccion", length = 250)
    private String direccion;

    @ManyToOne
    @JoinColumn(name = "distrito_id")
    private Distrito distrito;
}
