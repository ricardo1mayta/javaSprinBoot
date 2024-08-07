package pe.com.avivel.sistemas.guiaselectronicas.service.spec.security;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.security.SesionQueryDTO;

import java.util.Date;
import java.util.Optional;

public interface SesionQueryService {

    Optional<SesionQueryDTO> findByToken(String token);

    Page<SesionQueryDTO> listAllByFiltrosPaginado(Integer usuarioId, Date fechaInicio,
                                                  Date fechaFin, Pageable pageable);
}
