package pe.com.avivel.sistemas.guiaselectronicas.service.spec.security;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.security.RolQueryDTO;
import pe.com.avivel.sistemas.guiaselectronicas.entities.security.RolTipo;
import pe.com.avivel.sistemas.guiaselectronicas.entities.util.EstadoGeneral;

import java.util.List;
import java.util.Optional;

public interface RolQueryService {
    Optional<RolQueryDTO> findById(Integer id);

    List<RolQueryDTO> listByRolTipo(RolTipo tipo);

    List<RolQueryDTO> listByUsuarioId(Integer usuarioId);

    Page<RolQueryDTO> listAllByFiltrosPaginado(String nombre, EstadoGeneral estado,
                                               Pageable pageable);
}
