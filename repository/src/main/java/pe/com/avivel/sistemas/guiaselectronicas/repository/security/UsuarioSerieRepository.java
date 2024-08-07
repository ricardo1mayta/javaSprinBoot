package pe.com.avivel.sistemas.guiaselectronicas.repository.security;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.com.avivel.sistemas.guiaselectronicas.entities.security.UsuarioSerie;

@Repository
public interface UsuarioSerieRepository extends JpaRepository<UsuarioSerie, Integer> {

    @Query( "SELECT COUNT(US) > 0 " +
            "FROM UsuarioSerie US " +
            "WHERE US.usuario.id = :usuarioId " +
            "AND   US.serie.id = :serieId ")
    boolean exists(@Param("usuarioId") Integer usuarioId, @Param("serieId") Integer serieId);
}
