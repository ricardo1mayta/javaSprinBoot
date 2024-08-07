package pe.com.avivel.sistemas.guiaselectronicas.entities.envers;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;

import javax.persistence.*;
import java.beans.Transient;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "revision_aud", catalog = "bd_guias_electronicas_aud")
@RevisionEntity(EntityRevisionListener.class)
public class UsuarioRevisionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @RevisionNumber
    @Column(name = "revision_id")
    private int id;

    @RevisionTimestamp
    @Column(name = "revision_timestamp")
    private long timestamp;

    @Column(name = "revision_usuario", length = 30)
    private String username;

    @SuppressWarnings("unused")
    @Transient
    public Date getRevisionDate() {
        return new Date(this.timestamp);
    }
}
