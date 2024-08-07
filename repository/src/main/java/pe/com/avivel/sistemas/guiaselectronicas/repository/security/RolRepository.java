package pe.com.avivel.sistemas.guiaselectronicas.repository.security;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.com.avivel.sistemas.guiaselectronicas.entities.security.Rol;
import pe.com.avivel.sistemas.guiaselectronicas.entities.security.RolTipo;
import pe.com.avivel.sistemas.guiaselectronicas.entities.util.EstadoGeneral;
import pe.com.avivel.sistemas.guiaselectronicas.repository.util.DeleteRepository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface RolRepository extends DeleteRepository<Rol, Integer> {

    @Modifying
    @Query(value = "insert into seg_rol_menu(rol_id, menu_id) values(:rolId, :menuId)", nativeQuery = true)
    void insertRolMenu(@Param("rolId") Integer rolId, @Param("menuId") Integer menuId);

    @Modifying
    @Query(value = "delete from seg_rol_menu where rol_id = :rolId", nativeQuery = true)
    void deleteRolMenusByRolId(@Param("rolId") Integer rolId);

    @Query("SELECT R FROM Rol R WHERE LOWER(R.tipo) = LOWER(:#{#rolTipo.toString()}) AND R.activo = true ")
    List<Rol> listByRolTipo(@Param("rolTipo") RolTipo rolTipo);

    @Query("SELECT R FROM Usuario U JOIN U.roles R WHERE U.id = :usuarioId AND R.activo = true ")
    List<Rol> listByUsuarioId(@Param("usuarioId") Integer usuarioId);

    @Query("SELECT R " +
            "FROM Rol R " +
            "WHERE UPPER(R.nombre) LIKE UPPER(CONCAT('%',:nombre,'%')) " +
            "AND (UPPER(:#{#estado.toString()}) = 'T' OR R.activo = (CASE WHEN ( UPPER(:#{#estado.toString()}) = 'A' ) THEN true ELSE false END ) ) ")
    Page<Rol> listAllByFiltrosPaginado(@Param("nombre") String nombre,
                                       @Param("estado") EstadoGeneral estado, Pageable pageable);
}
