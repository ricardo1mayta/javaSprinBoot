package pe.com.avivel.sistemas.guiaselectronicas.repository.auxiliares.guia_remision;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pe.com.avivel.sistemas.guiaselectronicas.entities.auxiliares.guia_remision.UnidadMedida;

import java.util.List;
import java.util.Optional;

@Repository
public interface UnidadMedidaRepository extends JpaRepository<UnidadMedida, Integer> {

    @Query(" SELECT U FROM UnidadMedida U WHERE U.activo = true ")
    List<UnidadMedida> list();

    @Query("SELECT UM FROM UnidadMedida UM WHERE UM.codigoSunat=:codigoSunat")
    Optional<UnidadMedida> findByCodigoSunat(String codigoSunat);
}
