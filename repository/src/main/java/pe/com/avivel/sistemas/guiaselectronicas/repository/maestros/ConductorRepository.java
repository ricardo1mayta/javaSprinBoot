package pe.com.avivel.sistemas.guiaselectronicas.repository.maestros;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.maestros.FiltrosListadoAllConductores;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.maestros.FiltrosListadoConductores;
import pe.com.avivel.sistemas.guiaselectronicas.entities.maestros.Conductor;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
@Transactional
public interface ConductorRepository extends JpaRepository<Conductor, Integer> {
    @Query( "SELECT C " +
            "FROM Conductor C " +
            "WHERE (:#{#filtros.tipoDocumentoIdentidadId} = 0 OR C.tipoDocumentoIdentidad.id = :#{#filtros.tipoDocumentoIdentidadId} ) " +
            "AND   (:#{#filtros.nroDocumentoIdentidad} = '' OR UPPER(C.nroDocumentoIdentidad) LIKE UPPER(CONCAT('%', :#{#filtros.nroDocumentoIdentidad}, '%')) ) " +
            "AND   (:#{#filtros.nombre} = '' OR UPPER(C.nombres) LIKE UPPER(CONCAT('%', :#{#filtros.nombre}, '%')) " +
            "                                OR UPPER(C.apellidos) LIKE UPPER(CONCAT('%', :#{#filtros.nombre}, '%'))  ) " +
            "AND   (:#{#filtros.estado.toString()} = 'T' OR C.activo = :#{#filtros.estado.toBoolean()} ) ")
    Page<Conductor> listAllByFiltrosPaginado(@Param("filtros") FiltrosListadoAllConductores filtros, Pageable pageable);

    @Query( "SELECT C " +
            "FROM Conductor C " +
            "WHERE (:#{#filtros.tipoDocumentoIdentidadId} = 0 OR C.tipoDocumentoIdentidad.id = :#{#filtros.tipoDocumentoIdentidadId} ) " +
            "AND   (:#{#filtros.nroDocumentoIdentidad} = '' OR UPPER(C.nroDocumentoIdentidad) LIKE UPPER(CONCAT('%', :#{#filtros.nroDocumentoIdentidad}, '%')) ) " +
            "AND   (:#{#filtros.nombre} = '' OR UPPER(C.nombres) LIKE UPPER(CONCAT('%', :#{#filtros.nombre}, '%')) " +
            "                                OR UPPER(C.apellidos) LIKE UPPER(CONCAT('%', :#{#filtros.nombre}, '%'))  ) " +
            "AND   C.activo = true ")
    Page<Conductor> listByFiltrosPaginado(@Param("filtros") FiltrosListadoConductores filtros, Pageable pageable);


    @Query(" SELECT C " +
            "FROM Conductor C " +
            "WHERE C.tipoDocumentoIdentidad.id = :tipoDocumentoIdentidadId " +
            "AND   C.nroDocumentoIdentidad = :nroDocumentoIdentidad ")
    Optional<Conductor> findByTipoAndNroDocumentoIdentidad(
            @Param("tipoDocumentoIdentidadId") Integer tipoDocumentoIdentidadId,
            @Param("nroDocumentoIdentidad") String nroDocumentoIdentidad);

    @Modifying
    @Query("UPDATE Conductor C " +
            "SET C.activo= :activo, " +
            "C.lastModifiedBy = :#{authentication.name}, " +
            "C.lastModifiedDate = CURRENT_TIMESTAMP " +
            "WHERE C.id= :id")
    int updateEstadoById(@Param("id") Integer id, @Param("activo") boolean activo);
}
