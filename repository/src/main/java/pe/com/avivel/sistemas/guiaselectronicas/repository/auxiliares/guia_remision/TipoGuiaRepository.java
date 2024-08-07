package pe.com.avivel.sistemas.guiaselectronicas.repository.auxiliares.guia_remision;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.com.avivel.sistemas.guiaselectronicas.entities.auxiliares.guia_remision.TipoGuia;
import pe.com.avivel.sistemas.guiaselectronicas.repository.util.DeleteRepository;

@Repository
public interface TipoGuiaRepository extends DeleteRepository<TipoGuia, Integer>, JpaRepository<TipoGuia, Integer> {
}