package pe.com.avivel.sistemas.guiaselectronicas.repository.maestros.vehiculo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.maestros.vehiculo.FiltrosListadoAllVehiculos;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.maestros.vehiculo.FiltrosListadoVehiculos;
import pe.com.avivel.sistemas.guiaselectronicas.entities.maestros.vehiculo.Vehiculo;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
@Transactional
public interface VehiculoRepository extends JpaRepository<Vehiculo, Integer> {

    @Query("SELECT V FROM Vehiculo V WHERE V.placaPrincipal = :placaPrincipal ")
    Optional<Vehiculo> findByPlacaPrincipal(@Param("placaPrincipal") String placaPrincipal);

    @Query( "SELECT V " +
            "FROM Vehiculo V " +
            "WHERE (:#{#filtros.nroPlaca} = '' OR UPPER(V.placaPrincipal) LIKE UPPER(CONCAT('%', :#{#filtros.nroPlaca}, '%')) " +
            "                                  OR UPPER(V.placaSecundaria) LIKE UPPER(CONCAT('%', :#{#filtros.nroPlaca}, '%')) ) " +
            "AND   (:#{#filtros.descripcion} = '' OR UPPER(V.descripcion) LIKE UPPER(CONCAT('%', :#{#filtros.descripcion}, '%')) ) " +
            "AND   (:#{#filtros.estado.toString()} = 'T' OR V.activo = :#{#filtros.estado.toBoolean()} ) ")
    Page<Vehiculo> listAllByFiltrosPaginado(@Param("filtros") FiltrosListadoAllVehiculos filtros, Pageable pageable);

    @Query( "SELECT V " +
            "FROM Vehiculo V " +
            "WHERE (:#{#filtros.nroPlaca} = '' OR UPPER(V.placaPrincipal) LIKE UPPER(CONCAT('%', :#{#filtros.nroPlaca}, '%')) " +
            "                                  OR UPPER(V.placaSecundaria) LIKE UPPER(CONCAT('%', :#{#filtros.nroPlaca}, '%')) ) " +
            "AND   (:#{#filtros.descripcion} = '' OR UPPER(V.descripcion) LIKE UPPER(CONCAT('%', :#{#filtros.descripcion}, '%')) ) " +
            "AND   V.activo = true ")
    Page<Vehiculo> listByFiltrosPaginado(@Param("filtros") FiltrosListadoVehiculos filtros, Pageable pageable);

    @Modifying
    @Query("UPDATE Vehiculo V " +
            "SET V.activo= :activo, " +
            "V.lastModifiedBy = :#{authentication.name}, " +
            "V.lastModifiedDate = CURRENT_TIMESTAMP " +
            "WHERE V.id= :id")
    int updateEstadoById(@Param("id") Integer id, @Param("activo") boolean activo);
}
