package pe.com.avivel.sistemas.guiaselectronicas.repository.security;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.com.avivel.sistemas.guiaselectronicas.entities.security.Sesion;
import pe.com.avivel.sistemas.guiaselectronicas.repository.util.DeleteRepository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface SesionRepository extends DeleteRepository<Sesion, Integer>,JpaRepository<Sesion, Integer> {

    @Query("SELECT S FROM Sesion S WHERE S.token = :token ")
    Optional<Sesion> findByToken(@Param("token") String token);

    @Query("SELECT S FROM Sesion S " +
            "WHERE S.usuario.id = :usuarioId AND" +
            "(:fechaInicio IS NULL OR DATE(S.tokenCreacion) >= :fechaInicio) AND " +
            "(:fechaFin IS NULL OR DATE(S.tokenCreacion) <= :fechaFin)")
    Page<Sesion> listAllByFiltrosPaginado(@Param("usuarioId") Integer usuarioId,
                                          @Param("fechaInicio") Date fechaInicio,
                                          @Param("fechaFin") Date fechaFin,
                                          Pageable pageable);
}
