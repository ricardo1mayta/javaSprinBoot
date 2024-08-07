package pe.com.avivel.sistemas.guiaselectronicas.repository.auxiliares.guia_remision;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.com.avivel.sistemas.guiaselectronicas.entities.auxiliares.guia_remision.UnidadMedidaPesoBruto;

import java.util.List;
import java.util.Optional;

@Repository
public interface UnidadMedidaPesoBrutoRepository extends JpaRepository<UnidadMedidaPesoBruto, Integer> {

    @Query(" SELECT U FROM UnidadMedidaPesoBruto U WHERE U.activo = true ")
    List<UnidadMedidaPesoBruto> list();

    @Query("SELECT UMPB FROM UnidadMedidaPesoBruto UMPB WHERE UMPB.codigoSunat=:codigo")
    Optional<UnidadMedidaPesoBruto> findByCodigoSunat(@Param("codigo") String codigo);
}
