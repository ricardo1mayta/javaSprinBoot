package pe.com.avivel.sistemas.guiaselectronicas.service.spec.maestros.vehiculo;

import java.util.List;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.maestros.vehiculo.MarcaQueryDTO;

public interface MarcaQueryService {

    List<MarcaQueryDTO> list();
}
