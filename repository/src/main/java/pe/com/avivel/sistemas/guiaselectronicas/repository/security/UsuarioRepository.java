package pe.com.avivel.sistemas.guiaselectronicas.repository.security;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.com.avivel.sistemas.guiaselectronicas.entities.security.Usuario;
import pe.com.avivel.sistemas.guiaselectronicas.entities.util.EstadoGeneral;
import pe.com.avivel.sistemas.guiaselectronicas.repository.util.DeleteRepository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
@Transactional
public interface UsuarioRepository extends DeleteRepository<Usuario, Integer>, JpaRepository<Usuario, Integer> {

    Optional<Usuario> findByUsername(String username);

    Optional<Usuario> findByEmail(String email);

    @Query("SELECT U FROM Usuario U WHERE U.username = ?#{authentication?.name}")
    Optional<Usuario> findCurrentUser();

    @Modifying
    @Query("UPDATE Usuario U " +
            "SET U.password = :password, " +
            "U.lastModifiedBy = :#{authentication.name}, " +
            "U.lastModifiedDate = CURRENT_TIMESTAMP " +
            "WHERE U.id = :usuarioId ")
    int updatePassword(@Param("usuarioId") Integer usuarioId, @Param("password") String password);

    @Modifying
    @Query("UPDATE Usuario U " +
            "SET U.ultimoLogin = CURRENT_TIMESTAMP " +
            "WHERE U.username = :username")
    int updateLastLogin(@Param("username") String username);

    @Modifying
    @Query("update Usuario U " +
            "SET U.activo= :activo, " +
            "U.lastModifiedBy = :#{authentication.name}, " +
            "U.lastModifiedDate = CURRENT_TIMESTAMP " +
            "WHERE U.id= :usuarioId")
    Integer updateEstadoById(@Param("usuarioId") Integer usuarioId, @Param("activo") boolean activo);

    @Query(" SELECT U " +
            "FROM Usuario U " +
            "WHERE UPPER(U.username) LIKE UPPER(CONCAT('%',:username,'%')) " +
            "AND (UPPER(:#{#estado.toString()}) = 'T' OR U.activo = (CASE WHEN ( UPPER(:#{#estado.toString()}) = 'A' ) THEN true ELSE false END ) ) ")
    Page<Usuario> listAllByFiltrosPaginado(@Param("username") String username,
                                           @Param("estado") EstadoGeneral estado,
                                           Pageable pageable);
}
