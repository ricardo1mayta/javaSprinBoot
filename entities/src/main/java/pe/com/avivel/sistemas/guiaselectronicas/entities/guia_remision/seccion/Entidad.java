package pe.com.avivel.sistemas.guiaselectronicas.entities.guia_remision.seccion;

import lombok.*;
import pe.com.avivel.sistemas.guiaselectronicas.entities.auxiliares.TipoDocumentoIdentidad;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Embeddable
public class Entidad implements Serializable {

    private static final long serialVersionUID = 8298387521631758206L;

    @ManyToOne
    @JoinColumn(name = "tipo_documento_identidad_id")
    private TipoDocumentoIdentidad tipoDocumentoIdentidad;

    @Column(name = "nro_documento_identidad", length = 15)
    private String nroDocumentoIdentidad;

    @Column(name = "razon_social", length = 250)
    private String razonSocial;
}
