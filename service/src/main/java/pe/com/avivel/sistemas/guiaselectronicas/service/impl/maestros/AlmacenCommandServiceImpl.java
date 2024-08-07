package pe.com.avivel.sistemas.guiaselectronicas.service.impl.maestros;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.command.maestros.AlmacenCommandDTO;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.maestros.AlmacenQueryDTO;
import pe.com.avivel.sistemas.guiaselectronicas.entities.maestros.Almacen;
import pe.com.avivel.sistemas.guiaselectronicas.entities.ubigeos.Distrito;
import pe.com.avivel.sistemas.guiaselectronicas.repository.maestros.AlmacenRepository;
import pe.com.avivel.sistemas.guiaselectronicas.repository.ubigeos.DistritoRepository;
import pe.com.avivel.sistemas.guiaselectronicas.service.exceptions.ValidationException;
import pe.com.avivel.sistemas.guiaselectronicas.service.spec.maestros.AlmacenCommandService;

import java.util.Optional;

import static pe.com.avivel.sistemas.guiaselectronicas.service.impl.ConstansResponse.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class AlmacenCommandServiceImpl implements AlmacenCommandService {

    private final AlmacenRepository almacenRepository;

    private final DistritoRepository distritoRepository;

    private final ModelMapper modelMapper;

    @Override
    public AlmacenQueryDTO create(AlmacenCommandDTO dto) {
        Optional<Almacen> almacenOpByCodigoSunat = almacenRepository
                .findByCodigoSunat(dto.getCodigoSunat());

        if(almacenOpByCodigoSunat.isPresent()){
            throw new ValidationException(AlmacenResponse.EXISTE_CODIGO);
        }

        Almacen almacen = this.mapearAlmacenDesdeDTO(null, dto);

        Almacen almacenNew = almacenRepository.save(almacen);

        return modelMapper.map(almacenNew, AlmacenQueryDTO.class);
    }

    @Override
    public AlmacenQueryDTO update(Integer id, AlmacenCommandDTO dto) {
        Optional<Almacen> almacenOp = almacenRepository.findById(id);
        Optional<Almacen> almacenOpByCodigoSunat = almacenRepository
                .findByCodigoSunat(dto.getCodigoSunat());

        if(almacenOp.isEmpty()){
            throw new ValidationException(AlmacenResponse.NO_ENCONTRADO);
        }

        if(almacenOpByCodigoSunat.isPresent() &&
                !almacenOpByCodigoSunat.get().getId().equals(id) ){
            throw new ValidationException(AlmacenResponse.EXISTE_CODIGO);
        }

        Almacen almacen = this.mapearAlmacenDesdeDTO(almacenOp.get(), dto);

        Almacen almacenUpdate = almacenRepository.save(almacen);

        return modelMapper.map(almacenUpdate, AlmacenQueryDTO.class);
    }

    @Override
    public int updateEstadoById(Integer id, boolean activo) {
        Almacen almacen = almacenRepository.findById(id)
                .orElseThrow(() -> new ValidationException(AlmacenResponse.NO_ENCONTRADO));

        return almacenRepository.updateEstadoById(almacen.getId(), activo);
    }

    private Almacen mapearAlmacenDesdeDTO(Almacen almacen, AlmacenCommandDTO dto){

        if(almacen == null){
            almacen = modelMapper.map(dto, Almacen.class);
        } else {
            modelMapper.map(dto, almacen);
        }

        if(almacen.getDistrito() != null){
            Distrito distrito = distritoRepository
                    .findById(almacen.getDistrito().getId())
                    .orElseThrow(() -> new ValidationException(DistritoResponse.NO_ENCONTRADO));
            almacen.setDistrito(distrito);
        }

        return almacen;
    }
}
