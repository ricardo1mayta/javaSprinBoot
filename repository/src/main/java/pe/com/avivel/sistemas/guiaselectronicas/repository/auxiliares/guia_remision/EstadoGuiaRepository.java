package pe.com.avivel.sistemas.guiaselectronicas.repository.auxiliares.guia_remision;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.com.avivel.sistemas.guiaselectronicas.entities.auxiliares.guia_remision.EstadoGuia;

@Repository
public interface EstadoGuiaRepository extends JpaRepository<EstadoGuia, Integer> {
}
