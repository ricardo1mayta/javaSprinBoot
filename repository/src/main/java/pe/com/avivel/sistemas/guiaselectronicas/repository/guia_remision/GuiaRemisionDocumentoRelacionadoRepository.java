package pe.com.avivel.sistemas.guiaselectronicas.repository.guia_remision;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.com.avivel.sistemas.guiaselectronicas.entities.guia_remision.DocumentoRelacionado;

@Repository
public interface GuiaRemisionDocumentoRelacionadoRepository extends JpaRepository<DocumentoRelacionado, Integer> {
}
