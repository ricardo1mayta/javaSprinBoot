package pe.com.avivel.sistemas.guiaselectronicas.repository.maestros.entidad;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.maestros.entidad.FiltrosListadoAllEntidadAlmacenes;
import pe.com.avivel.sistemas.guiaselectronicas.entities.maestros.entidad.EntidadAlmacen;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface EntidadAlmacenRepository extends JpaRepository<EntidadAlmacen, Integer> {

    @Query("SELECT EA FROM EntidadAlmacen EA WHERE EA.entidadId = :entidadId AND EA.activo = true")
    List<EntidadAlmacen> listByEntidadId(@Param("entidadId") Integer entidadId);

    @Query( "SELECT EA " +
            "FROM EntidadAlmacen EA " +
            "WHERE EA.entidadId = :#{#filtros.entidadId} " +
            "AND   (:#{#filtros.descripcion} = '' OR UPPER(EA.descripcion) LIKE UPPER(CONCAT('%', :#{#filtros.descripcion}, '%')) ) " +
            "AND   (:#{#filtros.estado.toString()} = 'T' OR EA.activo = :#{#filtros.estado.toBoolean()} ) ")
    Page<EntidadAlmacen> listAllByFiltrosPaginado(@Param("filtros") FiltrosListadoAllEntidadAlmacenes filtros, Pageable pageable);

    @Modifying
    @Query("UPDATE EntidadAlmacen EA " +
            "SET EA.activo= :activo, " +
            "EA.lastModifiedBy = :#{authentication.name}, " +
            "EA.lastModifiedDate = CURRENT_TIMESTAMP " +
            "WHERE EA.id= :id")
    int updateEstadoById(@Param("id") Integer id, @Param("activo") boolean activo);
}
