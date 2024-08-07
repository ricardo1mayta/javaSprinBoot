package pe.com.avivel.sistemas.guiaselectronicas.entities.security;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.Audited;
import pe.com.avivel.sistemas.guiaselectronicas.entities.audit.Auditable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@ToString
@Getter
@Setter
@Audited
@AuditOverride(forClass = Auditable.class)
@Entity
@Access(AccessType.FIELD)
@Table(name = "seg_menu")
//@SQLDelete(sql = "UPDATE seg_menu SET deleted = true, fecha_eliminacion = CURRENT_TIMESTAMP  WHERE id=?")
//@Where(clause = "deleted = 0")
public class Menu extends Auditable implements Serializable {

    private static final long serialVersionUID = -5568888020922079865L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "path", nullable = false)
    private String path;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "icon", nullable = false)
    private String icon;

    @Column(name = "class", nullable = false)
    private String clazz;

    @Column(name = "badge", nullable = false)
    private String badge;

    @Column(name = "badge_class", nullable = false)
    private String badgeClass;

    @Column(name = "is_external_link", nullable = false)
    private boolean externalLink = false;

    @Column(name = "orden", nullable = false)
    private Integer orden;

    @ToString.Exclude
    @Getter(AccessLevel.NONE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    private Menu menu;

    @Column(name = "menu_id", insertable = false, updatable = false)
    private Integer menuId;

    @OneToMany(mappedBy = "menu", fetch = FetchType.LAZY)
    @OrderBy("orden asc")
    @ToString.Exclude
    private List<Menu> subMenus;

    @ColumnDefault("1")
    @Column(name = "is_visible", nullable = false)
    private boolean visible = true;
}
