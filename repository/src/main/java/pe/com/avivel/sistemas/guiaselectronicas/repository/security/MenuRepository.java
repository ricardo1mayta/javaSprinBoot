package pe.com.avivel.sistemas.guiaselectronicas.repository.security;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.com.avivel.sistemas.guiaselectronicas.entities.security.Menu;
import pe.com.avivel.sistemas.guiaselectronicas.repository.util.DeleteRepository;

import java.util.List;

@Repository
public interface MenuRepository extends DeleteRepository<Menu, Integer>,JpaRepository<Menu, Integer> {

    @Query(value =  "select * " +
                    "from seg_menu " +
                    "   where activo = 1 and menu_id is null " +
                    "order by orden ", nativeQuery = true)
    List<Menu> listParents();

    @Query(value =  "select distinct m.* " +
                    "from seg_menu m " +
                    "   where m.activo = 1 and m.menu_id = :menuId " +
                    "order by m.orden ", nativeQuery = true)
    List<Menu> listSubMenusByMenuId(@Param("menuId") Integer menuId);

    @Query(value =  "select distinct m.* " +
                    "from seg_usuario u " +
                    "   join seg_usuario_rol ur on u.id = ur.usuario_id " +
                    "   join seg_rol r on r.id = ur.rol_id and r.activo = 1 " +
                    "   join seg_rol_menu rm on r.id = rm.rol_id " +
                    "   join seg_menu m on rm.menu_id = m.id and m.activo = 1 " +
                    "   left join seg_menu mp on m.menu_id = mp.id and mp.activo = 1 " +
                    "   where u.activo = 1 and u.id = :usuarioId " +
                    "order by ifnull(mp.orden,m.orden), " +
                    " (case when mp.orden is null then 0 else m.orden end) ", nativeQuery = true)
    List<Menu> listByUsuarioIdSinJerarquias(@Param("usuarioId") Integer usuarioId);

    @Query(value = "select distinct m.* " +
            "from seg_usuario u " +
            "   join seg_usuario_rol ur on u.id = ur.usuario_id " +
            "   join seg_rol r on ur.rol_id = r.id and r.activo = 1 " +
            "   join seg_rol_menu rm on r.id = rm.rol_id " +
            "   join seg_menu m " +
            "       on rm.menu_id = m.id and m.menu_id is null and m.activo = 1 " +
            "   where u.activo = 1 and  u.id = :usuarioId " +
            "order by m.orden ", nativeQuery = true)
    List<Menu> listParentsByUsuarioId(@Param("usuarioId") Integer usuarioId);

    @Query(value = "select distinct m.* " +
            "from seg_usuario u " +
            "   join seg_usuario_rol ur on u.id = ur.usuario_id " +
            "   join seg_rol r on ur.rol_id = r.id and r.activo = 1 " +
            "   join seg_rol_menu rm on rm.rol_id = r.id " +
            "   join seg_menu m on rm.menu_id = m.id and m.activo = 1 " +
            "   where u.activo = 1 and u.id = :usuarioId and m.menu_id = :menuId " +
            "order by m.orden ", nativeQuery = true)
    List<Menu> listSubMenusByMenuIdAndUsuarioId(@Param("menuId") Integer menuId, @Param("usuarioId") Integer usuarioId);

    @Query(value = "select distinct m.* " +
            " from seg_rol_menu rm " +
            "   join seg_rol r on rm.rol_id = r.id and r.activo = 1 " +
            "   join seg_menu m on rm.menu_id = m.id and m.activo = 1 " +
            "   left join seg_menu mp on m.menu_id = mp.id and mp.activo = 1 " +
            "   where rm.rol_id = :rolId " +
            "order by ifnull(mp.orden,m.orden), " +
            " (case when mp.orden is null then 0 else m.orden end) ", nativeQuery = true)
    List<Menu> listByRolIdSinJerarquias(@Param("rolId") Integer rolId);

    @Query(value = "select distinct m.* " +
            " from seg_rol_menu rm " +
            "   join seg_rol r " +
            "       on rm.rol_id = r.id and r.activo = 1 " +
            "   join seg_menu m on rm.menu_id = m.id and m.menu_id is null and m.activo = 1 " +
            "   where rm.rol_id = :rolId " +
            " order by m.orden ", nativeQuery = true)
    List<Menu> listParentsByRolId(@Param("rolId") Integer rolId);

    @Query(value = "select distinct m.* " +
            "from seg_rol_menu rm " +
            "   join seg_rol r on rm.rol_id = r.id and r.activo = 1 " +
            "   join seg_menu m on rm.menu_id = m.id and m.activo = 1 " +
            "   where rm.rol_id = :rolId and m.menu_id = :menuId " +
            "order by m.orden ", nativeQuery = true)
    List<Menu> listSubMenusByMenuIdAndRolId(@Param("menuId") Integer menuId, @Param("rolId") Integer rolId);
}
