package pe.com.avivel.sistemas.guiaselectronicas.service.impl.maestros.vehiculo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.command.maestros.vehiculo.VehiculoCommandDTO;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.maestros.vehiculo.VehiculoQueryDTO;
import pe.com.avivel.sistemas.guiaselectronicas.entities.maestros.vehiculo.Categoria;
import pe.com.avivel.sistemas.guiaselectronicas.entities.maestros.vehiculo.Marca;
import pe.com.avivel.sistemas.guiaselectronicas.entities.maestros.vehiculo.Vehiculo;
import pe.com.avivel.sistemas.guiaselectronicas.repository.maestros.vehiculo.CategoriaRepository;
import pe.com.avivel.sistemas.guiaselectronicas.repository.maestros.vehiculo.MarcaRepository;
import pe.com.avivel.sistemas.guiaselectronicas.repository.maestros.vehiculo.VehiculoRepository;
import pe.com.avivel.sistemas.guiaselectronicas.service.exceptions.ValidationException;
import pe.com.avivel.sistemas.guiaselectronicas.service.spec.maestros.vehiculo.VehiculoCommandService;

import java.util.Optional;

import static pe.com.avivel.sistemas.guiaselectronicas.service.impl.ConstansResponse.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class VehiculoCommandServiceImpl implements VehiculoCommandService {

    private final VehiculoRepository vehiculoRepository;

    private final CategoriaRepository categoriaRepository;

    private final MarcaRepository marcaRepository;

    private final ModelMapper modelMapper;

    @Override
    public VehiculoQueryDTO create(VehiculoCommandDTO dto) {
        Optional<Vehiculo> vehiculoOpByPlacaPrincipal = vehiculoRepository
                .findByPlacaPrincipal(dto.getPlacaPrincipal());

        if(vehiculoOpByPlacaPrincipal.isPresent()){
            throw new ValidationException(VehiculoResponse.EXISTE_PLACA);
        }

        Vehiculo vehiculo = this.mapearVehiculoDesdeDTO(null, dto);

        Vehiculo vehiculoNew = vehiculoRepository.save(vehiculo);

        return modelMapper.map(vehiculoNew, VehiculoQueryDTO.class);
    }

    @Override
    public VehiculoQueryDTO update(Integer id, VehiculoCommandDTO dto) {
        Optional<Vehiculo> vehiculoOp = vehiculoRepository.findById(id);
        Optional<Vehiculo> vehiculoOpByPlacaPrincipal = vehiculoRepository
                .findByPlacaPrincipal(dto.getPlacaPrincipal());

        if(vehiculoOp.isEmpty()){
            throw new ValidationException(VehiculoResponse.NO_ENCONTRADO);
        }

        if(vehiculoOpByPlacaPrincipal.isPresent() &&
                !vehiculoOpByPlacaPrincipal.get().getId().equals(id) ){
            throw new ValidationException(VehiculoResponse.EXISTE_PLACA);
        }

        Vehiculo vehiculo = this.mapearVehiculoDesdeDTO(vehiculoOp.get(), dto);

        Vehiculo vehiculoUpdate = vehiculoRepository.save(vehiculo);

        return modelMapper.map(vehiculoUpdate, VehiculoQueryDTO.class);
    }

    @Override
    public int updateEstadoById(Integer id, boolean activo) {
        Vehiculo vehiculo = vehiculoRepository.findById(id)
                .orElseThrow(() -> new ValidationException(VehiculoResponse.NO_ENCONTRADO));

        return vehiculoRepository.updateEstadoById(vehiculo.getId(), activo);
    }

    private Vehiculo mapearVehiculoDesdeDTO(Vehiculo vehiculo, VehiculoCommandDTO dto){

        if(vehiculo == null){
            vehiculo = modelMapper.map(dto, Vehiculo.class);
        } else {
            modelMapper.map(dto, vehiculo);
        }

        if(vehiculo.getMarca() != null){
            Marca marca = marcaRepository
                    .findById(vehiculo.getMarca().getId())
                    .orElseThrow(() -> new ValidationException(MarcaResponse.NO_ENCONTRADO));
            vehiculo.setMarca(marca);
        }

        if(vehiculo.getCategoria() != null){
            Categoria categoria = categoriaRepository
                    .findById(vehiculo.getCategoria().getId())
                    .orElseThrow(() -> new ValidationException(CategoriaResponse.NO_ENCONTRADO));
            vehiculo.setCategoria(categoria);
        }

        return vehiculo;
    }
}
