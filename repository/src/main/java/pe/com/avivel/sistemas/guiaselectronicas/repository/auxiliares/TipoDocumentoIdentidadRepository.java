package pe.com.avivel.sistemas.guiaselectronicas.repository.auxiliares;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.com.avivel.sistemas.guiaselectronicas.entities.auxiliares.TipoDocumentoIdentidad;

import java.util.List;
import java.util.Optional;

@Repository
public interface TipoDocumentoIdentidadRepository extends JpaRepository<TipoDocumentoIdentidad, Integer> {

    @Query("SELECT TDI FROM TipoDocumentoIdentidad TDI where TDI.activo = true")
    List<TipoDocumentoIdentidad> list();


    @Query("SELECT TDI FROM TipoDocumentoIdentidad TDI WHERE TDI.codigoSunat=:codigoSunat")
    Optional<TipoDocumentoIdentidad> findByTipoDocumentoCodigoSunat(@Param("codigoSunat") String codigoSunat);
}
