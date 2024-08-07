package pe.com.avivel.sistemas.guiaselectronicas.repository.ubigeos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.com.avivel.sistemas.guiaselectronicas.entities.ubigeos.Provincia;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProvinciaRepository extends JpaRepository<Provincia, Integer> {

    @Query("SELECT P FROM Provincia P WHERE P.activo = true AND P.departamento.id = :departamentoId ")
    List<Provincia> listByDepartamentoId(@Param("departamentoId") Integer departamentoId);


}
