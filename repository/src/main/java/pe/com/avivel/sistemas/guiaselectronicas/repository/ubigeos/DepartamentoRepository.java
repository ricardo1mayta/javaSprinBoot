package pe.com.avivel.sistemas.guiaselectronicas.repository.ubigeos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.com.avivel.sistemas.guiaselectronicas.entities.ubigeos.Departamento;

import java.util.List;

@Repository
public interface DepartamentoRepository extends JpaRepository<Departamento, Integer> {

    @Query("SELECT D FROM Departamento D WHERE D.activo = true AND D.pais.id = :paisId ")
    List<Departamento> listByPaisId(@Param("paisId") Integer paisId);
}
