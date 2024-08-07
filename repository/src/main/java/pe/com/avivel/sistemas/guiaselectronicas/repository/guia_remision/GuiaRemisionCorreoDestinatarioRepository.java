package pe.com.avivel.sistemas.guiaselectronicas.repository.guia_remision;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.com.avivel.sistemas.guiaselectronicas.entities.guia_remision.CorreoDestinatario;

@Repository
public interface GuiaRemisionCorreoDestinatarioRepository extends JpaRepository<CorreoDestinatario, Integer> {
}
