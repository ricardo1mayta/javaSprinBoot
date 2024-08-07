package pe.com.avivel.sistemas.guiaselectronicas.repository.maestros.vehiculo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pe.com.avivel.sistemas.guiaselectronicas.entities.maestros.vehiculo.Categoria;

import java.util.List;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {

    @Query(" SELECT C FROM Categoria C WHERE C.activo = true ")
    List<Categoria> list();
}
