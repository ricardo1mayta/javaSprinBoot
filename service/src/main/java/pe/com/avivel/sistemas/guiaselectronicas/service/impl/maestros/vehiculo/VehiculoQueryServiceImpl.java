package pe.com.avivel.sistemas.guiaselectronicas.service.impl.maestros.vehiculo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.maestros.vehiculo.FiltrosListadoAllVehiculos;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.maestros.vehiculo.FiltrosListadoVehiculos;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.maestros.vehiculo.VehiculoQueryDTO;
import pe.com.avivel.sistemas.guiaselectronicas.repository.maestros.vehiculo.VehiculoRepository;
import pe.com.avivel.sistemas.guiaselectronicas.service.spec.maestros.vehiculo.VehiculoQueryService;

@Slf4j
@Service
@RequiredArgsConstructor
public class VehiculoQueryServiceImpl implements VehiculoQueryService {

    private final VehiculoRepository vehiculoRepository;

    private final ModelMapper modelMapper;

    @Override
    public Page<VehiculoQueryDTO> listAllByFiltrosPaginado(FiltrosListadoAllVehiculos filtros, Pageable pageable) {
        return vehiculoRepository.listAllByFiltrosPaginado(filtros, pageable)
                .map(v -> modelMapper.map(v, VehiculoQueryDTO.class));
    }

    @Override
    public Page<VehiculoQueryDTO> listByFiltrosPaginado(FiltrosListadoVehiculos filtros, Pageable pageable) {
        return vehiculoRepository.listByFiltrosPaginado(filtros, pageable)
                .map(v -> modelMapper.map(v, VehiculoQueryDTO.class));
    }
}
