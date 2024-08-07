package pe.com.avivel.sistemas.guiaselectronicas.repository.maestros;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.maestros.FiltrosListadoAllArticulos;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.maestros.FiltrosListadoArticulos;
import pe.com.avivel.sistemas.guiaselectronicas.entities.maestros.Articulo;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
@Transactional
public interface ArticuloRepository extends JpaRepository<Articulo, Integer> {

    @Query("SELECT A FROM Articulo A WHERE A.codigo = :codigo ")
    Optional<Articulo> findByCodigo(@Param("codigo") String codigo);

    @Query( "SELECT A " +
            "FROM Articulo A " +
            "WHERE (:#{#filtros.codigo} = '' OR UPPER(A.codigo) LIKE UPPER(CONCAT('%', :#{#filtros.codigo}, '%')) ) " +
            "AND   (:#{#filtros.descripcion} = '' OR UPPER(A.descripcion) LIKE UPPER(CONCAT('%', :#{#filtros.descripcion}, '%')) ) " +
            "AND   (:#{#filtros.estado.toString()} = 'T' OR A.activo = :#{#filtros.estado.toBoolean()} ) ")
    Page<Articulo> listAllByFiltrosPaginado(@Param("filtros") FiltrosListadoAllArticulos filtros, Pageable pageable);

    @Query( "SELECT A " +
            "FROM Articulo A " +
            "LEFT JOIN UsuarioArticulo UA ON UA.usuario.id = :#{authentication.datosSesion.usuarioId} AND UA.articulo.id = A.id " +
            "WHERE (:#{#filtros.codigo} = '' OR UPPER(A.codigo) LIKE UPPER(CONCAT('%', :#{#filtros.codigo}, '%')) ) " +
            "AND   (:#{#filtros.descripcion} = '' OR UPPER(A.descripcion) LIKE UPPER(CONCAT('%', :#{#filtros.descripcion}, '%')) ) " +
            "AND   (:#{authentication.datosSesion.usuarioExterno} = false OR UA.id IS NOT NULL)" +
            "AND   A.activo = true ")
    Page<Articulo> listByFiltrosPaginado(@Param("filtros") FiltrosListadoArticulos filtros, Pageable pageable);

    @Modifying
    @Query("UPDATE Articulo A " +
            "SET A.activo= :activo, " +
            "A.lastModifiedBy = :#{authentication.name}, " +
            "A.lastModifiedDate = CURRENT_TIMESTAMP " +
            "WHERE A.id= :id")
    int updateEstadoById(@Param("id") Integer id, @Param("activo") boolean activo);
}
