package pe.com.avivel.sistemas.guiaselectronicas.repository.maestros.vehiculo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pe.com.avivel.sistemas.guiaselectronicas.entities.maestros.vehiculo.Marca;

import java.util.List;

@Repository
public interface MarcaRepository extends JpaRepository<Marca, Integer> {

    @Query(" SELECT M FROM Marca M WHERE M.activo = true ")
    List<Marca> list();
}
