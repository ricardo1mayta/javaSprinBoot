package pe.com.avivel.sistemas.guiaselectronicas.service.spec.maestros.vehiculo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.maestros.vehiculo.FiltrosListadoAllVehiculos;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.maestros.vehiculo.FiltrosListadoVehiculos;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.maestros.vehiculo.VehiculoQueryDTO;

public interface VehiculoQueryService {

    Page<VehiculoQueryDTO> listAllByFiltrosPaginado(FiltrosListadoAllVehiculos filtros, Pageable pageable);

    Page<VehiculoQueryDTO> listByFiltrosPaginado(FiltrosListadoVehiculos filtros, Pageable pageable);
}
