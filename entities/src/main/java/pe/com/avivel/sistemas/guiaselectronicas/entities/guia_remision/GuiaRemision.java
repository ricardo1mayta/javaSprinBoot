package pe.com.avivel.sistemas.guiaselectronicas.entities.guia_remision;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.envers.Audited;
import pe.com.avivel.sistemas.guiaselectronicas.entities.audit.Auditable;
import pe.com.avivel.sistemas.guiaselectronicas.entities.auxiliares.guia_remision.EstadoGuia;
import pe.com.avivel.sistemas.guiaselectronicas.entities.auxiliares.guia_remision.TipoGuia;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Audited
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "gre_guia_remision", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"serie", "numero"} )})
//@SQLDelete(sql = "UPDATE gre_guia_remision SET deleted = true, fecha_eliminacion = CURRENT_TIMESTAMP  WHERE id=?")
//@Where(clause = "deleted = 0")
public class GuiaRemision extends Auditable implements Serializable {

    private static final long serialVersionUID = 9018536096343951861L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "version_ubl", length = 3, nullable = false)
    private String versionUbl = "2.1";

    @Column(name = "version_estructura", length = 3, nullable = false)
    private String versionEstructura = "2.0";

    @Column(name = "serie", length = 6, nullable = false)
    private String serie;

    @Column(name = "numero", length = 9, nullable = false, updatable = false)
    private String numero;

    @ManyToOne
    @JoinColumn(name = "tipo_guia_id", nullable = false)
    private TipoGuia tipoGuia;

    @Column(name = "orden_pedido", length = 50)
    private String ordenPedido;

    @Column(name = "orden_compra", length = 50)
    private String ordenCompra;

    @Column(name = "usuario_emision", length = 500)
    private String usuarioEmision;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_emision")
    private Date fechaEmision;

    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_vencimiento")
    private Date fechaVencimiento;

    @Column(name = "observacion", length = 250, nullable = false)
    private String observacion;

    @Column(name = "nro_items", nullable = false)
    private Integer nroItems = 0;

    @Column(name = "efact_ticket")
    private String efactTicket;

    @Column(name = "efact_digest_value", length = 64)
    private String efactDigestValue;

    @Column(name = "efact_observacion", columnDefinition = "text")
    private String efactObservacion;

    //TODO: Eliminar campo
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_generacion_xml")
    private Date fechaGeneracionXml;

    //TODO: Eliminar campo
    @Column(name = "archivo", length = 400)
    private String archivo;

    @ColumnDefault("0")
    @Column(name = "efact_cdr_descargado", nullable = false)
    private boolean efactCdrDescargado = false;

    @ColumnDefault("0")
    @Column(name = "efact_xml_descargado", nullable = false)
    private boolean efactXmlDescargado = false;

    @ColumnDefault("0")
    @Column(name = "efact_pdf_descargado", nullable = false)
    private boolean efactPdfDescargado = false;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "guiaRemision")
    private List<DocumentoRelacionado> documentoRelacionados;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "guiaRemision")
    private List<CorreoDestinatario> correoDestinatarios;

    @OneToMany(mappedBy = "guiaRemision", cascade = CascadeType.ALL)
    private List<LogEstado> logEstados;

    @ManyToOne
    @JoinColumn(name = "estado_id", nullable = false)
    private EstadoGuia estado;
}
