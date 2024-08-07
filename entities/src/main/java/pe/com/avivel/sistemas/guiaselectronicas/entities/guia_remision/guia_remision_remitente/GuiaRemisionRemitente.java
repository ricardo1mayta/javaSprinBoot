package pe.com.avivel.sistemas.guiaselectronicas.entities.guia_remision.guia_remision_remitente;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.envers.Audited;
import pe.com.avivel.sistemas.guiaselectronicas.commons.empresa.EmpresaProperties;
import pe.com.avivel.sistemas.guiaselectronicas.commons.files.TypeFile;
import pe.com.avivel.sistemas.guiaselectronicas.entities.guia_remision.GuiaRemision;
import pe.com.avivel.sistemas.guiaselectronicas.entities.guia_remision.guia_remision_remitente.detalle.Articulo;
import pe.com.avivel.sistemas.guiaselectronicas.entities.guia_remision.guia_remision_remitente.detalle.Conductor;
import pe.com.avivel.sistemas.guiaselectronicas.entities.guia_remision.guia_remision_remitente.detalle.Vehiculo;
import pe.com.avivel.sistemas.guiaselectronicas.entities.guia_remision.seccion.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@ToString
@Getter
@Setter
@Audited
@Entity
@PrimaryKeyJoinColumn(name = "guia_remision_id")
@Table(name = "grr_guia_remision_remitente")
//@SQLDelete(sql = "UPDATE grr_guia_remision_remitente SET deleted = true, fecha_eliminacion = CURRENT_TIMESTAMP  WHERE id=?")
//@Where(clause = "deleted = 0")
public class GuiaRemisionRemitente extends GuiaRemision implements Serializable {

    private static final long serialVersionUID = -3071112518210340035L;

    @Embedded
    @AssociationOverrides({
            @AssociationOverride(name = "tipoDocumentoIdentidad", joinColumns = @JoinColumn(name = "dest_tipo_documento_identidad_id", nullable = false)),
            @AssociationOverride(name = "distrito", joinColumns = @JoinColumn(name = "dest_distrito_id"))
    })
    @AttributeOverrides({
            @AttributeOverride(name = "nroDocumentoIdentidad", column = @Column(name = "dest_nro_documento_identidad", length = 15, nullable = false)),
            @AttributeOverride(name = "razonSocial", column = @Column(name = "dest_razon_social", length = 250, nullable = false)),
            @AttributeOverride(name = "direccion", column = @Column(name = "dest_direccion", length = 250, nullable = false)),

    })
    private Destinatario destinatario;

    @Embedded
    @AssociationOverrides({
            @AssociationOverride(name = "motivoTraslado", joinColumns = @JoinColumn(name = "env_motivo_traslado_id", nullable = false)),
            @AssociationOverride(name = "unidadMedidaPesoBruto", joinColumns = @JoinColumn(name = "env_unidad_medida_peso_bruto_id", nullable = false)),
            @AssociationOverride(name = "modalidadTraslado", joinColumns = @JoinColumn(name = "env_modalidad_traslado_id", nullable = false))
    })
    @AttributeOverrides({
            @AttributeOverride(name = "identificadorTraslado", column = @Column(name = "env_identificador_traslado", length = 11, nullable = false)),
            @AttributeOverride(name = "fechaInicioTraslado", column = @Column(name = "env_fecha_inicio_traslado")),
            @AttributeOverride(name = "fechaEntregaBienesTransportista", column = @Column(name = "env_fecha_entrega_bienes_transportista")),
            @AttributeOverride(name = "descripcionMotivoTraslado", column = @Column(name = "env_descripcion_motivo_traslado", length = 100, nullable = false)),
            @AttributeOverride(name = "pesoBruto", column = @Column(name = "env_peso_bruto", columnDefinition = "numeric(15,3)", nullable = false)),
            @AttributeOverride(name = "trasladoVehiculosM1L", column = @Column(name = "env_is_traslado_vehiculos_m1_l", nullable = false))
    })
    private Envio envio;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "guiaRemisionRemitente")
    private List<Conductor> conductores;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "guiaRemisionRemitente")
    private List<Vehiculo> vehiculos;

    @Embedded
    @AssociationOverrides({
            @AssociationOverride(name = "tipoDocumentoIdentidad", joinColumns = @JoinColumn(name = "trp_tipo_documento_identidad_id"))
    })
    @AttributeOverrides({
            @AttributeOverride(name = "nroDocumentoIdentidad", column = @Column(name = "trp_nro_documento_identidad", length = 15)),
            @AttributeOverride(name = "razonSocial", column = @Column(name = "trp_razon_social", length = 250)),
            @AttributeOverride(name = "nroRegistroMTC", column = @Column(name = "trp_nro_registro_MTC", length = 20))
    })
    private Transportista transportista;

    @Embedded
    @AssociationOverrides({
            @AssociationOverride(name = "tipoDocumentoIdentidad", joinColumns = @JoinColumn(name = "prv_tipo_documento_identidad_id"))
    })
    @AttributeOverrides({
            @AttributeOverride(name = "nroDocumentoIdentidad", column = @Column(name = "prv_nro_documento_identidad", length = 15)),
            @AttributeOverride(name = "razonSocial", column = @Column(name = "prv_razon_social", length = 250))
    })
    private Entidad proveedor;

    @Embedded
    @AssociationOverrides({
            @AssociationOverride(name = "tipoDocumentoIdentidad", joinColumns = @JoinColumn(name = "cmp_tipo_documento_identidad_id"))
    })
    @AttributeOverrides({
            @AttributeOverride(name = "nroDocumentoIdentidad", column = @Column(name = "cmp_nro_documento_identidad", length = 15)),
            @AttributeOverride(name = "razonSocial", column = @Column(name = "cmp_razon_social", length = 250))
    })
    private Entidad comprador;

    @Embedded
    @AssociationOverrides({
            @AssociationOverride(name = "almacen", joinColumns = @JoinColumn(name = "ppt_almacen_id")),
            @AssociationOverride(name = "entidadAlmacen", joinColumns = @JoinColumn(name = "ppt_entidad_almacen_id")),
            @AssociationOverride(name = "distrito", joinColumns = @JoinColumn(name = "ppt_distrito_id", nullable = false))
    })
    @AttributeOverrides({
            @AttributeOverride(name = "almacenInterno", column = @Column(name = "ppt_is_almacen_interno", nullable = false)),
            @AttributeOverride(name = "direccion", column = @Column(name = "ppt_direccion", length = 500, nullable = false))
    })
    private Direccion puntoPartida;

    @Embedded
    @AssociationOverrides({
            @AssociationOverride(name = "almacen", joinColumns = @JoinColumn(name = "pll_almacen_id")),
            @AssociationOverride(name = "entidadAlmacen", joinColumns = @JoinColumn(name = "pll_entidad_almacen_id")),
            @AssociationOverride(name = "distrito", joinColumns = @JoinColumn(name = "pll_distrito_id"))
    })
    @AttributeOverrides({
            @AttributeOverride(name = "almacenInterno", column = @Column(name = "pll_is_almacen_interno")),
            @AttributeOverride(name = "direccion", column = @Column(name = "pll_direccion", length = 500))
    })
    private Direccion puntoLlegada;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "guiaRemisionRemitente")
    private List<Articulo> articulos;

    public String getNombreGuiaElectronica(){
        return EmpresaProperties.getInstance().getRuc()+"-"+ this.getTipoGuia().getCodigoSunat()+"-"+this.getNumeroSerie();
    }

    public String getNombreGuiaElectronica(TypeFile typeFile){
        return this.getNombreGuiaElectronica() + typeFile.getExtension();
    }

    public String getNumeroSerie(){
        return this.getSerie()+"-"+this.getNumero();
    }

    @PrePersist
    private void prePersist(){
        this.setNroItems(this.getArticulos() != null ? this.getArticulos().size() : 0);
    }
    @PreUpdate
    public void preUpdate(){
        this.setNroItems(this.getArticulos() != null ? this.getArticulos().size() : 0);
    }
}