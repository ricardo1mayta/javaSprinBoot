package pe.com.avivel.sistemas.guiaselectronicas.service.impl.maestros;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.command.maestros.ConductorCommandDTO;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.maestros.ConductorQueryDTO;
import pe.com.avivel.sistemas.guiaselectronicas.entities.auxiliares.TipoDocumentoIdentidad;
import pe.com.avivel.sistemas.guiaselectronicas.entities.maestros.Conductor;
import pe.com.avivel.sistemas.guiaselectronicas.repository.auxiliares.TipoDocumentoIdentidadRepository;
import pe.com.avivel.sistemas.guiaselectronicas.repository.maestros.ConductorRepository;
import pe.com.avivel.sistemas.guiaselectronicas.service.exceptions.ValidationException;
import pe.com.avivel.sistemas.guiaselectronicas.service.spec.maestros.ConductorCommandService;

import java.util.Optional;

import static pe.com.avivel.sistemas.guiaselectronicas.service.impl.ConstansResponse.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConductorCommandServiceImpl implements ConductorCommandService {

    private final ConductorRepository conductorRepository;

    private final TipoDocumentoIdentidadRepository tipoDocumentoIdentidadRepository;

    private final ModelMapper modelMapper;

    @Override
    public ConductorQueryDTO create(ConductorCommandDTO dto) {
        Optional<Conductor> conductorOpByTipoAndNroDocumentoIdentidad = conductorRepository
                .findByTipoAndNroDocumentoIdentidad(dto.getTipoDocumentoIdentidad().getId(), dto.getNroDocumentoIdentidad());

        if(conductorOpByTipoAndNroDocumentoIdentidad.isPresent()){
            throw new ValidationException(ConductorResponse.EXISTE_TIPO_NRO_DOCUMENTO);
        }

        Conductor conductor = this.mapearConductorDesdeDTO(null, dto);

        Conductor conductorNew = conductorRepository.save(conductor);

        return modelMapper.map(conductorNew, ConductorQueryDTO.class);
    }

    @Override
    public ConductorQueryDTO update(Integer id, ConductorCommandDTO dto) {
        Optional<Conductor> conductorOp = conductorRepository.findById(id);
        Optional<Conductor> conductorOpByTipoAndNroDocumentoIdentidad = conductorRepository
                .findByTipoAndNroDocumentoIdentidad(dto.getTipoDocumentoIdentidad().getId(), dto.getNroDocumentoIdentidad());

        if(conductorOp.isEmpty()){
            throw new ValidationException(ConductorResponse.NO_ENCONTRADO);
        }

        if(conductorOpByTipoAndNroDocumentoIdentidad.isPresent() &&
                !conductorOpByTipoAndNroDocumentoIdentidad.get().getId().equals(id) ){
            throw new ValidationException(ConductorResponse.EXISTE_TIPO_NRO_DOCUMENTO);
        }

        Conductor conductor = this.mapearConductorDesdeDTO(conductorOp.get(), dto);

        Conductor conductorUpdate = conductorRepository.save(conductor);

        return modelMapper.map(conductorUpdate, ConductorQueryDTO.class);
    }

    @Override
    public int updateEstadoById(Integer id, boolean activo) {
        Conductor conductor = conductorRepository.findById(id)
                .orElseThrow(() -> new ValidationException(ConductorResponse.NO_ENCONTRADO));

        return conductorRepository.updateEstadoById(conductor.getId(), activo);
    }

    private Conductor mapearConductorDesdeDTO(Conductor conductor, ConductorCommandDTO dto){

        if(conductor == null){
            conductor = modelMapper.map(dto, Conductor.class);
        } else {
            modelMapper.map(dto, conductor);
        }

        if(conductor.getTipoDocumentoIdentidad() != null){
            TipoDocumentoIdentidad tipoDocumentoIdentidad = tipoDocumentoIdentidadRepository
                    .findById(conductor.getTipoDocumentoIdentidad().getId())
                    .orElseThrow(() -> new ValidationException(TipoDocumentoIdentidadResponse.NO_ENCONTRADO));
            conductor.setTipoDocumentoIdentidad(tipoDocumentoIdentidad);
        }

        return conductor;
    }
}
