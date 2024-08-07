package pe.com.avivel.sistemas.guiaselectronicas.repository.guia_remision;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.com.avivel.sistemas.guiaselectronicas.entities.guia_remision.GuiaRemision;

import java.util.Optional;

public interface GuiaRemisionRepository extends JpaRepository<GuiaRemision, Integer> {

    @Query("SELECT G.id FROM GuiaRemision G WHERE G.serie = :serie AND G.numero = :numero ")
    Optional<Integer> findBySerieAndNumero(@Param("serie") String serie, @Param("numero") String numero);

    @Query("SELECT G.estado.id FROM GuiaRemision G WHERE G.id = :id ")
    Integer findEstadoById(@Param("id") Integer id);

    @Query(value =  "select gr.numero " +
                    "from gre_guia_remision gr " +
                    "where gr.serie = :serie " +
                    "order by gr.numero desc " +
                    "limit 1 ", nativeQuery = true)
    String findLastCorrelativoBySerie(@Param("serie") String serie);
}
