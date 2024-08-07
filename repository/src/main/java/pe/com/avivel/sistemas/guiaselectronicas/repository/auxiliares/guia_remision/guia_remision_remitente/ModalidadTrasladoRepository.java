package pe.com.avivel.sistemas.guiaselectronicas.repository.auxiliares.guia_remision.guia_remision_remitente;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.com.avivel.sistemas.guiaselectronicas.entities.auxiliares.guia_remision.guia_remision_remitente.ModalidadTraslado;

import java.util.List;
import java.util.Optional;

@Repository
public interface ModalidadTrasladoRepository extends JpaRepository<ModalidadTraslado, Integer> {

    @Query(" SELECT M FROM ModalidadTraslado M WHERE M.activo = true ")
    List<ModalidadTraslado> list();

    @Query("SELECT MT FROM ModalidadTraslado MT WHERE MT.codigoSunat=:codigo")
    Optional<ModalidadTraslado> findByCodigoSunat(@Param("codigo") String codigo);
}
