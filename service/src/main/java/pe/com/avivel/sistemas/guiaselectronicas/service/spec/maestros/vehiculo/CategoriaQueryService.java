package pe.com.avivel.sistemas.guiaselectronicas.service.spec.maestros.vehiculo;

import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.maestros.vehiculo.CategoriaQueryDTO;

import java.util.List;

public interface CategoriaQueryService {

    List<CategoriaQueryDTO> list();
}
