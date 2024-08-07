package pe.com.avivel.sistemas.guiaselectronicas.repository.auxiliares;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.com.avivel.sistemas.guiaselectronicas.entities.auxiliares.TipoDocumento;

import java.util.List;
import java.util.Optional;

@Repository
public interface TipoDocumentoRepository extends JpaRepository<TipoDocumento, Integer> {

    @Query("SELECT TD FROM TipoDocumento TD where TD.activo = true")
    List<TipoDocumento> list();

    @Query("SELECT TD FROM TipoDocumento TD WHERE TD.abreviatura=:abreviatura")
    Optional<TipoDocumento> findByAbreviatura(@Param("abreviatura") String abreviatura);
}
