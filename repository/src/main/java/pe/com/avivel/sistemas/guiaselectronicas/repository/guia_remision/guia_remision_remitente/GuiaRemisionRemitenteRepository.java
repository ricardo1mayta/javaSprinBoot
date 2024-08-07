package pe.com.avivel.sistemas.guiaselectronicas.repository.guia_remision.guia_remision_remitente;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.guia_remision.guia_remision_remitente.FiltrosListadoGuias;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.guia_remision.guia_remision_remitente.FiltrosReporteGuiasElaboradas;
import pe.com.avivel.sistemas.guiaselectronicas.entities.guia_remision.guia_remision_remitente.GuiaRemisionRemitente;
import pe.com.avivel.sistemas.guiaselectronicas.repository.util.DeleteRepository;

@Repository
public interface GuiaRemisionRemitenteRepository extends DeleteRepository<GuiaRemisionRemitente, Integer>,
        JpaRepository<GuiaRemisionRemitente, Integer> {

    @Query( "SELECT GRR " +
            "FROM GuiaRemisionRemitente GRR " +
            "WHERE (:#{#filtros.serie} = '' OR UPPER(GRR.serie) LIKE UPPER(CONCAT('%',:#{#filtros.serie},'%')) ) " +
            "AND   (:#{#filtros.numero} = '' OR UPPER(GRR.numero) LIKE UPPER(CONCAT('%',:#{#filtros.numero},'%')) ) " +
            "AND   (:#{#filtros.motivoTrasladoId} = 0 OR GRR.envio.motivoTraslado.id = :#{#filtros.motivoTrasladoId} ) " +
            "AND   (:#{#filtros.fechaEmision} IS NULL OR DATE(GRR.fechaEmision) = DATE(:#{#filtros.fechaEmision}) ) "+
            "AND   (:#{#filtros.nroDocumentoIdentidad} = '' OR UPPER(GRR.destinatario.nroDocumentoIdentidad) LIKE UPPER(CONCAT('%',:#{#filtros.nroDocumentoIdentidad},'%')) ) " +
            "AND   (:#{#filtros.razonSocial} = '' OR UPPER(GRR.destinatario.razonSocial) LIKE UPPER(CONCAT('%',:#{#filtros.razonSocial},'%')) ) ")
    Page<GuiaRemisionRemitente> listByFiltrosPaginados(@Param("filtros") FiltrosListadoGuias filtros, Pageable pageable);

    @Query( "SELECT GRR " +
            "FROM GuiaRemisionRemitente GRR " +
            "WHERE (:#{#filtros.serie} = '' OR UPPER(GRR.serie) LIKE UPPER(CONCAT('%',:#{#filtros.serie},'%')) ) " +
            "AND   (:#{#filtros.motivoTrasladoId} = 0 OR GRR.envio.motivoTraslado.id = :#{#filtros.motivoTrasladoId} ) " +
            "AND   DATE(GRR.fechaEmision) >= DATE(:#{#filtros.fechaDesde}) " +
            "AND   DATE(GRR.fechaEmision) <= DATE(:#{#filtros.fechaHasta}) " +
            "AND   (:#{#filtros.estadoId} = 0 OR GRR.estado.id = :#{#filtros.estadoId} ) " +
            "AND   (:#{#filtros.destinatarioTipoDocumentoIdentidadId} = 0 OR GRR.destinatario.tipoDocumentoIdentidad.id = :#{#filtros.destinatarioTipoDocumentoIdentidadId} ) " +
            "AND   (:#{#filtros.destinatarioNroDocumentoIdentidad} = '' OR UPPER(GRR.destinatario.nroDocumentoIdentidad) LIKE UPPER(CONCAT('%',:#{#filtros.destinatarioNroDocumentoIdentidad},'%')) ) " +
            "AND   (:#{#filtros.destinatarioRazonSocial} = '' OR UPPER(GRR.destinatario.razonSocial) LIKE UPPER(CONCAT('%',:#{#filtros.destinatarioRazonSocial},'%')) ) ")
    Page<GuiaRemisionRemitente> listReporteGuiasElaboradasByFiltrosPaginados(@Param("filtros") FiltrosReporteGuiasElaboradas filtros, Pageable pageable);
}
