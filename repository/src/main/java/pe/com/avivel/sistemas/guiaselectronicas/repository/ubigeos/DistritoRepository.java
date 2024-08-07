package pe.com.avivel.sistemas.guiaselectronicas.repository.ubigeos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.com.avivel.sistemas.guiaselectronicas.entities.ubigeos.Distrito;

import java.util.List;
import java.util.Optional;

@Repository
public interface DistritoRepository extends JpaRepository<Distrito, Integer> {

    @Query("SELECT D FROM Distrito D WHERE D.activo = true AND D.provincia.id = :provinciaId ")
    List<Distrito> listByProvinciaId(@Param("provinciaId") Integer provinciaId);


    @Query("SELECT D FROM Distrito D WHERE D.codigoUbigeo=:codigo")
    Optional<Distrito> findByCodigoUbigeo(@Param("codigo") String codigo);

    @Query("SELECT D FROM Distrito D WHERE D.descripcion=:descripcion")
    Optional<Distrito> findByDescripcion(@Param("descripcion") String descripcion);
}
