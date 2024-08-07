package pe.com.avivel.sistemas.guiaselectronicas.repository.maestros;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.com.avivel.sistemas.guiaselectronicas.entities.maestros.ArticuloUnidadMedida;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArticuloUnidadMedidaRepository extends JpaRepository<ArticuloUnidadMedida, Integer> {

    @Query(" SELECT AUM " +
            "FROM ArticuloUnidadMedida AUM " +
            "WHERE AUM.articuloId = :articuloId " +
            "AND   AUM.unidadMedida.id = :unidadId ")
    Optional<ArticuloUnidadMedida> findByArticuloIdAndUnidadId(@Param("articuloId") Integer articuloId,
                                                               @Param("unidadId") Integer unidadId);

    @Query("SELECT AUM " +
            "FROM ArticuloUnidadMedida AUM " +
            "WHERE AUM.articuloId = :articuloId ")
    List<ArticuloUnidadMedida> listAllByArticuloId(@Param("articuloId") Integer articuloId);
}
