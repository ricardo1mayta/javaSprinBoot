package pe.com.avivel.sistemas.guiaselectronicas.repository.maestros;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.maestros.FiltrosListadoAllAlmacenes;
import pe.com.avivel.sistemas.guiaselectronicas.entities.maestros.Almacen;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface AlmacenRepository extends JpaRepository<Almacen, Integer> {

    @Query("SELECT A FROM Almacen A where A.activo = true")
    List<Almacen> list();

    @Query( "SELECT A " +
            "FROM Almacen A " +
            "WHERE (:#{#filtros.codigoSunat} = '' OR UPPER(A.codigoSunat) LIKE UPPER(CONCAT('%', :#{#filtros.codigoSunat}, '%')) ) " +
            "AND   (:#{#filtros.descripcion} = '' OR UPPER(A.descripcion) LIKE UPPER(CONCAT('%', :#{#filtros.descripcion}, '%')) ) " +
            "AND   (:#{#filtros.estado.toString()} = 'T' OR A.activo = :#{#filtros.estado.toBoolean()} ) ")
    Page<Almacen> listAllByFiltrosPaginado(@Param("filtros") FiltrosListadoAllAlmacenes filtros, Pageable pageable);

    @Query("SELECT A FROM Almacen A WHERE A.codigoSunat=:codigo")
    Optional<Almacen> findByCodigoSunat(@Param("codigo") String codigo);

    @Modifying
    @Query("UPDATE Almacen A " +
            "SET A.activo= :activo, " +
            "A.lastModifiedBy = :#{authentication.name}, " +
            "A.lastModifiedDate = CURRENT_TIMESTAMP " +
            "WHERE A.id= :id")
    int updateEstadoById(@Param("id") Integer id, @Param("activo") boolean activo);
}
