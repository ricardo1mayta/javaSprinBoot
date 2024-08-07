package pe.com.avivel.sistemas.guiaselectronicas.entities.audit;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Auditable implements Serializable {

    private static final long serialVersionUID = -2920488204507562113L;

    @ColumnDefault("1")
    @Column(name = "activo", nullable = false)
    private boolean activo = true;

    @CreatedBy
    @Column(name = "usuario_creacion", length = 100, nullable = false, updatable = false)
    protected String createdBy;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    protected Date creationDate;

    @LastModifiedBy
    @Column(name = "usuario_modificacion", length = 100)
    protected String lastModifiedBy;

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_modificacion")
    protected Date lastModifiedDate;

//    @ColumnDefault("0")
//    @Column(name = "deleted")
//    protected boolean deleted = false;
//
//    //@DeletedBy //No existe EventListener para el evento de eliminar
//    @Column(name = "usuario_eliminacion", length = 100)
//    protected String deletedBy;
//
//    @Temporal(TemporalType.TIMESTAMP)
//    @Column(name = "fecha_eliminacion")
//    private Date deletedDate;
}

