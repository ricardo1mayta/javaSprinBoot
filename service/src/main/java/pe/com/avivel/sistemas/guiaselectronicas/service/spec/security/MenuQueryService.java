package pe.com.avivel.sistemas.guiaselectronicas.service.spec.security;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.security.MenuQueryDTO;

import java.util.List;
import java.util.Optional;

public interface MenuQueryService {

    Optional<MenuQueryDTO> findById(Integer id);

    List<MenuQueryDTO> list();

    List<MenuQueryDTO> listByUsuarioIdSinJerarquias(Integer usuarioId);

    List<MenuQueryDTO> listByUsuarioId(Integer usuarioId);

    List<MenuQueryDTO> listByRolIdSinJerarquias(Integer rolId);

    List<MenuQueryDTO> listByRolId(Integer rolId);
}
