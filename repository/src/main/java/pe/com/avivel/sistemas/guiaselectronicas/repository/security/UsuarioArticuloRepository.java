package pe.com.avivel.sistemas.guiaselectronicas.repository.security;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.com.avivel.sistemas.guiaselectronicas.entities.security.UsuarioArticulo;

@Repository
public interface UsuarioArticuloRepository extends JpaRepository<UsuarioArticulo, Integer> {

    @Query( "SELECT COUNT(UA) > 0 " +
            "FROM UsuarioArticulo UA " +
            "WHERE UA.usuario.id = :usuarioId " +
            "AND   UA.articulo.id = :articuloId ")
    boolean exists(@Param("usuarioId") Integer usuarioId, @Param("articuloId") Integer articuloId);
}
