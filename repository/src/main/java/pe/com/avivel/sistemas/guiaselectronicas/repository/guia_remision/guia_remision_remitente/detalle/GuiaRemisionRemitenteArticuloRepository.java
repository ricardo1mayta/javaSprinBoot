package pe.com.avivel.sistemas.guiaselectronicas.repository.guia_remision.guia_remision_remitente.detalle;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.com.avivel.sistemas.guiaselectronicas.entities.guia_remision.guia_remision_remitente.detalle.Articulo;

@Repository
public interface GuiaRemisionRemitenteArticuloRepository  extends JpaRepository<Articulo, Integer> {
}
