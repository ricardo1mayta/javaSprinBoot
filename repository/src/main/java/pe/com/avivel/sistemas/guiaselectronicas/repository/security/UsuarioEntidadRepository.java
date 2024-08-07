package pe.com.avivel.sistemas.guiaselectronicas.repository.security;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.com.avivel.sistemas.guiaselectronicas.entities.security.UsuarioEntidad;

@Repository
public interface UsuarioEntidadRepository extends JpaRepository<UsuarioEntidad, Integer> {

    @Query( "SELECT COUNT(UE) > 0 " +
            "FROM UsuarioEntidad UE " +
            "WHERE UE.usuario.id = :usuarioId " +
            "AND   UE.entidad.id = :entidadId ")
    boolean exists(@Param("usuarioId") Integer usuarioId, @Param("entidadId") Integer entidadId);
}
