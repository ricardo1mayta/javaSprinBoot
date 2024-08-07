package pe.com.avivel.sistemas.guiaselectronicas.repository.maestros.entidad;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.maestros.entidad.FiltrosListadoAllEntidadCorreos;
import pe.com.avivel.sistemas.guiaselectronicas.entities.maestros.entidad.EntidadCorreo;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface EntidadCorreoRepository extends JpaRepository<EntidadCorreo, Integer> {

    @Query("SELECT EC FROM EntidadCorreo EC WHERE EC.entidadId = :entidadId AND EC.activo = true")
    List<EntidadCorreo> listByEntidadId(@Param("entidadId") Integer entidadId);

    @Query( "SELECT EC " +
            "FROM EntidadCorreo EC " +
            "WHERE EC.entidadId = :#{#filtros.entidadId} " +
            "AND   (:#{#filtros.correo} = '' OR UPPER(EC.correo) LIKE UPPER(CONCAT('%', :#{#filtros.correo}, '%')) ) " +
            "AND   (:#{#filtros.estado.toString()} = 'T' OR EC.activo = :#{#filtros.estado.toBoolean()} ) ")
    Page<EntidadCorreo> listAllByFiltrosPaginado(@Param("filtros") FiltrosListadoAllEntidadCorreos filtros, Pageable pageable);

    @Query(" SELECT EC " +
            "FROM EntidadCorreo EC " +
            "WHERE EC.correo = :correo " +
            "AND   EC.entidad.id = :entidadId ")
    Optional<EntidadCorreo> findByCorreoAndEntidadId(@Param("correo") String correo,
                                                     @Param("entidadId") Integer entidadId);

    @Modifying
    @Query("UPDATE EntidadCorreo EC " +
            "SET EC.activo= :activo, " +
            "EC.lastModifiedBy = :#{authentication.name}, " +
            "EC.lastModifiedDate = CURRENT_TIMESTAMP " +
            "WHERE EC.id= :id")
    int updateEstadoById(@Param("id") Integer id, @Param("activo") boolean activo);
}
