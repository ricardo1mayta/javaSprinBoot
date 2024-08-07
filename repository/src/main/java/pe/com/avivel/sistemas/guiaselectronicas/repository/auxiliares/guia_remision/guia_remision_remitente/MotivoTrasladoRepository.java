package pe.com.avivel.sistemas.guiaselectronicas.repository.auxiliares.guia_remision.guia_remision_remitente;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pe.com.avivel.sistemas.guiaselectronicas.entities.auxiliares.guia_remision.guia_remision_remitente.MotivoTraslado;
import pe.com.avivel.sistemas.guiaselectronicas.repository.util.DeleteRepository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MotivoTrasladoRepository extends DeleteRepository<MotivoTraslado, Integer>, JpaRepository<MotivoTraslado, Integer> {

    @Query(" SELECT M FROM MotivoTraslado M WHERE M.activo = true ")
    List<MotivoTraslado> list();

    @Query("SELECT M FROM ModalidadTraslado M WHERE M.codigoSunat=:codigo")
    Optional<MotivoTraslado> findByCodigo(String codigo);
}
