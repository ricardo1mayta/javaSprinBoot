package pe.com.avivel.sistemas.guiaselectronicas.repository.maestros;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.maestros.FiltrosListadoAllSeries;
import pe.com.avivel.sistemas.guiaselectronicas.entities.maestros.Serie;
import pe.com.avivel.sistemas.guiaselectronicas.repository.util.DeleteRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface SerieRepository extends DeleteRepository<Serie, Integer>, JpaRepository<Serie, Integer> {

    @Query("SELECT S FROM Serie S WHERE S.codigo = :codigo AND S.activo = true")
    Optional<Serie> findByCodigo(@Param("codigo") String codigo);

    @Query("SELECT S FROM Serie S WHERE S.codigo = :codigo")
    Optional<Serie> findAllByCodigo(@Param("codigo") String codigo);

    @Query("SELECT S FROM Serie S WHERE S.activo = true")
    List<Serie> list();

    @Query( "SELECT S " +
            "FROM Serie S " +
            "WHERE (:#{#filtros.codigo} = '' OR UPPER(S.codigo) LIKE UPPER(CONCAT('%', :#{#filtros.codigo}, '%')) ) " +
            "AND   (:#{#filtros.descripcion} = '' OR UPPER(S.descripcion) LIKE UPPER(CONCAT('%', :#{#filtros.descripcion}, '%')) ) " +
            "AND   (:#{#filtros.estado.toString()} = 'T' OR S.activo = :#{#filtros.estado.toBoolean()} ) ")
    Page<Serie> listAllByFiltrosPaginado(@Param("filtros") FiltrosListadoAllSeries filtros, Pageable pageable);

    @Modifying
    @Query("UPDATE Serie S " +
            "SET S.activo= :activo, " +
            "S.lastModifiedBy = :#{authentication.name}, " +
            "S.lastModifiedDate = CURRENT_TIMESTAMP " +
            "WHERE S.id= :id")
    int updateEstadoById(@Param("id") Integer id, @Param("activo") boolean activo);
}
