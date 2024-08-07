package pe.com.avivel.sistemas.guiaselectronicas.repository.maestros.entidad;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.maestros.entidad.FiltrosListadoAllEntidades;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.maestros.entidad.FiltrosListadoEntidades;
import pe.com.avivel.sistemas.guiaselectronicas.entities.maestros.entidad.Entidad;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
@Transactional
public interface EntidadRepository extends JpaRepository<Entidad, Integer> {

    @Query( "SELECT E " +
            "FROM Entidad E " +
            "WHERE (:#{#filtros.tipoDocumentoIdentidadId} = 0 OR E.tipoDocumentoIdentidad.id = :#{#filtros.tipoDocumentoIdentidadId} ) " +
            "AND   (:#{#filtros.nroDocumentoIdentidad} = '' OR UPPER(E.nroDocumentoIdentidad) LIKE UPPER(CONCAT('%', :#{#filtros.nroDocumentoIdentidad}, '%')) ) " +
            "AND   (:#{#filtros.razonSocial} = '' OR UPPER(E.razonSocial) LIKE UPPER(CONCAT('%', :#{#filtros.razonSocial}, '%')) ) " +
            "AND   (:#{#filtros.estado.toString()} = 'T' OR E.activo = :#{#filtros.estado.toBoolean()} ) ")
    Page<Entidad> listAllByFiltrosPaginado(@Param("filtros") FiltrosListadoAllEntidades filtros, Pageable pageable);

    @Query( "SELECT E " +
            "FROM Entidad E " +
            "LEFT JOIN UsuarioEntidad UE ON UE.usuario.id = :#{authentication.datosSesion.usuarioId} AND UE.entidad.id = E.id " +
            "WHERE (:#{#filtros.tipoDocumentoIdentidadId} = 0 OR E.tipoDocumentoIdentidad.id = :#{#filtros.tipoDocumentoIdentidadId} ) " +
            "AND   (:#{#filtros.nroDocumentoIdentidad} = '' OR UPPER(E.nroDocumentoIdentidad) LIKE UPPER(CONCAT('%', :#{#filtros.nroDocumentoIdentidad}, '%')) ) " +
            "AND   (:#{#filtros.razonSocial} = '' OR UPPER(E.razonSocial) LIKE UPPER(CONCAT('%', :#{#filtros.razonSocial}, '%')) ) " +
            "AND   (:#{authentication.datosSesion.usuarioExterno} = false OR UE.id IS NOT NULL)" +
            "AND   E.activo = true ")
    Page<Entidad> listByFiltrosPaginado(@Param("filtros") FiltrosListadoEntidades filtros, Pageable pageable);

    @Query(" SELECT E " +
            "FROM Entidad E " +
            "WHERE E.tipoDocumentoIdentidad.id = :tipoDocumentoIdentidadId " +
            "AND   E.nroDocumentoIdentidad = :nroDocumentoIdentidad ")
    Optional<Entidad> findByTipoAndNroDocumentoIdentidad(
            @Param("tipoDocumentoIdentidadId") Integer tipoDocumentoIdentidadId,
            @Param("nroDocumentoIdentidad") String nroDocumentoIdentidad);

    @Modifying
    @Query("UPDATE Entidad E " +
            "SET E.activo= :activo, " +
            "E.lastModifiedBy = :#{authentication.name}, " +
            "E.lastModifiedDate = CURRENT_TIMESTAMP " +
            "WHERE E.id= :id")
    int updateEstadoById(@Param("id") Integer id, @Param("activo") boolean activo);
}
