package pe.com.avivel.sistemas.guiaselectronicas.entities.security;


import lombok.Getter;
import lombok.experimental.FieldNameConstants;
import static pe.com.avivel.sistemas.guiaselectronicas.commons.util.Util.ROLE_PREFIX;

/**
 * Enum para roles de tipo permiso
 */
@Getter
@FieldNameConstants(onlyExplicitlyIncluded = true)
public enum Roles {
    @FieldNameConstants.Include _ADMIN(1),
    @FieldNameConstants.Include _CONTABILIDAD(2),
    @FieldNameConstants.Include _ALMACEN(3),
    @FieldNameConstants.Include ADMIN(4),
    @FieldNameConstants.Include CONTABILIDAD(5),
    @FieldNameConstants.Include ALMACEN(6),
    @FieldNameConstants.Include _OPERARIO(7),
    @FieldNameConstants.Include OPERARIO(8),
    @FieldNameConstants.Include _ALMACEN_JEFATURA(9),
    @FieldNameConstants.Include ALMACEN_JEFATURA(10);

    private final Integer id;

    private final String authority;

    Roles(Integer id){
        this.id = id;
        this.authority = ROLE_PREFIX + this;
    }
}
