package pe.com.avivel.sistemas.guiaselectronicas.service.impl.maestros.entidad;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.command.maestros.entidad.EntidadAlmacenCommandDTO;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.command.maestros.entidad.EntidadCommandDTO;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.command.maestros.entidad.EntidadCorreoCommandDTO;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.maestros.entidad.EntidadAlmacenQueryDTO;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.maestros.entidad.EntidadCorreoQueryDTO;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.maestros.entidad.EntidadQueryDTO;
import pe.com.avivel.sistemas.guiaselectronicas.entities.auxiliares.TipoDocumentoIdentidad;
import pe.com.avivel.sistemas.guiaselectronicas.entities.maestros.entidad.Entidad;
import pe.com.avivel.sistemas.guiaselectronicas.entities.maestros.entidad.EntidadAlmacen;
import pe.com.avivel.sistemas.guiaselectronicas.entities.maestros.entidad.EntidadCorreo;
import pe.com.avivel.sistemas.guiaselectronicas.entities.ubigeos.Distrito;
import pe.com.avivel.sistemas.guiaselectronicas.repository.auxiliares.TipoDocumentoIdentidadRepository;
import pe.com.avivel.sistemas.guiaselectronicas.repository.maestros.entidad.EntidadAlmacenRepository;
import pe.com.avivel.sistemas.guiaselectronicas.repository.maestros.entidad.EntidadCorreoRepository;
import pe.com.avivel.sistemas.guiaselectronicas.repository.maestros.entidad.EntidadRepository;
import pe.com.avivel.sistemas.guiaselectronicas.repository.ubigeos.DistritoRepository;
import pe.com.avivel.sistemas.guiaselectronicas.service.exceptions.ValidationException;
import pe.com.avivel.sistemas.guiaselectronicas.service.spec.maestros.entidad.EntidadCommandService;

import java.util.Optional;

import static pe.com.avivel.sistemas.guiaselectronicas.service.impl.ConstansResponse.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class EntidadCommandServiceImpl implements EntidadCommandService {

    private final EntidadRepository entidadRepository;

    private final EntidadAlmacenRepository entidadAlmacenRepository;

    private final EntidadCorreoRepository entidadCorreoRepository;

    private final TipoDocumentoIdentidadRepository tipoDocumentoIdentidadRepository;

    private final DistritoRepository distritoRepository;

    private final ModelMapper modelMapper;

    @Override
    public EntidadQueryDTO create(EntidadCommandDTO dto) {
        Optional<Entidad> entidadOpByTipoAndNroDocumentoIdentidad = entidadRepository
                .findByTipoAndNroDocumentoIdentidad(dto.getTipoDocumentoIdentidad().getId(), dto.getNroDocumentoIdentidad());

        if(entidadOpByTipoAndNroDocumentoIdentidad.isPresent()){
            throw new ValidationException(EntidadResponse.EXISTE_TIPO_NRO_DOCUMENTO);
        }

        Entidad entidad = this.mapearEntidadDesdeDTO(null, dto);

        Entidad entidadNew = entidadRepository.save(entidad);

        return modelMapper.map(entidadNew, EntidadQueryDTO.class);
    }

    @Override
    public EntidadQueryDTO update(Integer id, EntidadCommandDTO dto) {
        Optional<Entidad> entidadOp = entidadRepository.findById(id);
        Optional<Entidad> entidadOpByTipoAndNroDocumentoIdentidad = entidadRepository
                .findByTipoAndNroDocumentoIdentidad(dto.getTipoDocumentoIdentidad().getId(), dto.getNroDocumentoIdentidad());

        if(entidadOp.isEmpty()){
            throw new ValidationException(EntidadResponse.NO_ENCONTRADA);
        }

        if(entidadOpByTipoAndNroDocumentoIdentidad.isPresent() &&
                !entidadOpByTipoAndNroDocumentoIdentidad.get().getId().equals(id) ){
            throw new ValidationException(EntidadResponse.EXISTE_TIPO_NRO_DOCUMENTO);
        }

        Entidad entidad = this.mapearEntidadDesdeDTO(entidadOp.get(), dto);

        Entidad entidadUpdate = entidadRepository.save(entidad);

        return modelMapper.map(entidadUpdate, EntidadQueryDTO.class);
    }

    @Override
    public int updateEstadoById(Integer id, boolean activo) {
        Entidad entidad = entidadRepository.findById(id)
                .orElseThrow(() -> new ValidationException(EntidadResponse.NO_ENCONTRADA));

        return entidadRepository.updateEstadoById(entidad.getId(), activo);
    }

    @Override
    public EntidadAlmacenQueryDTO createAlmacen(EntidadAlmacenCommandDTO dto) {
        EntidadAlmacen entidadAlmacen = this.mapearEntidadAlmacenDesdeDTO(null, dto);

        EntidadAlmacen entidadAlmacenNew = entidadAlmacenRepository.save(entidadAlmacen);

        return modelMapper.map(entidadAlmacenNew, EntidadAlmacenQueryDTO.class);
    }

    @Override
    public EntidadAlmacenQueryDTO updateAlmacen(Integer id, EntidadAlmacenCommandDTO dto) {
        Optional<EntidadAlmacen> entidadAlmacenOp = entidadAlmacenRepository.findById(id);

        if(entidadAlmacenOp.isEmpty()){
            throw new ValidationException(EntidadResponse.ALMACEN_NO_ENCONTRADO);
        }

        EntidadAlmacen entidadAlmacen = this.mapearEntidadAlmacenDesdeDTO(entidadAlmacenOp.get(), dto);

        EntidadAlmacen entidadAlmacenUpdate = entidadAlmacenRepository.save(entidadAlmacen);

        return modelMapper.map(entidadAlmacenUpdate, EntidadAlmacenQueryDTO.class);
    }

    @Override
    public int updateEstadoAlmacenById(Integer id, boolean activo) {
        EntidadAlmacen entidadAlmacen = entidadAlmacenRepository.findById(id)
                .orElseThrow(() -> new ValidationException(EntidadResponse.ALMACEN_NO_ENCONTRADO));

        return entidadAlmacenRepository.updateEstadoById(entidadAlmacen.getId(), activo);
    }

    @Override
    public EntidadCorreoQueryDTO createCorreo(EntidadCorreoCommandDTO dto) {
        Optional<EntidadCorreo> entidadCorreoByCorreoOp = entidadCorreoRepository
                .findByCorreoAndEntidadId(dto.getCorreo(), dto.getEntidadId());

        if(entidadCorreoByCorreoOp.isPresent()){
            throw new ValidationException(EntidadResponse.EXISTE_CORREO);
        }

        EntidadCorreo entidadCorreo = this.mapearEntidadCorreoDesdeDTO(null, dto);

        EntidadCorreo entidadCorreoNew = entidadCorreoRepository.save(entidadCorreo);

        return modelMapper.map(entidadCorreoNew, EntidadCorreoQueryDTO.class);
    }

    @Override
    public EntidadCorreoQueryDTO updateCorreo(Integer id, EntidadCorreoCommandDTO dto) {
        Optional<EntidadCorreo> entidadCorreoByCorreoOp = entidadCorreoRepository
                .findByCorreoAndEntidadId(dto.getCorreo(), dto.getEntidadId());
        Optional<EntidadCorreo> entidadCorreoOp = entidadCorreoRepository.findById(id);

        if(entidadCorreoByCorreoOp.isPresent()){
            throw new ValidationException(EntidadResponse.EXISTE_CORREO);
        }

        if(entidadCorreoOp.isEmpty()){
            throw new ValidationException(EntidadResponse.CORREO_NO_ENCONTRADO);
        }

        EntidadCorreo entidadCorreo = this.mapearEntidadCorreoDesdeDTO(entidadCorreoOp.get(), dto);

        EntidadCorreo entidadCorreoUpdate = entidadCorreoRepository.save(entidadCorreo);

        return modelMapper.map(entidadCorreoUpdate, EntidadCorreoQueryDTO.class);
    }

    @Override
    public int updateEstadoCorreoById(Integer id, boolean activo) {
        EntidadCorreo entidadCorreo = entidadCorreoRepository.findById(id)
                .orElseThrow(() -> new ValidationException(EntidadResponse.CORREO_NO_ENCONTRADO));

        return entidadCorreoRepository.updateEstadoById(entidadCorreo.getId(), activo);
    }

    private Entidad mapearEntidadDesdeDTO(Entidad entidad, EntidadCommandDTO dto){

        if(entidad == null){
            entidad = modelMapper.map(dto, Entidad.class);
        } else {
            modelMapper.map(dto, entidad);
        }

        if(entidad.getTipoDocumentoIdentidad() != null){
            TipoDocumentoIdentidad tipoDocumentoIdentidad = tipoDocumentoIdentidadRepository
                    .findById(entidad.getTipoDocumentoIdentidad().getId())
                    .orElseThrow(() -> new ValidationException(TipoDocumentoIdentidadResponse.NO_ENCONTRADO));
            entidad.setTipoDocumentoIdentidad(tipoDocumentoIdentidad);
        }

        if(entidad.getDistrito() != null){
            Distrito distrito = distritoRepository
                    .findById(entidad.getDistrito().getId())
                    .orElseThrow(() -> new ValidationException(DistritoResponse.NO_ENCONTRADO));
            entidad.setDistrito(distrito);
        }

        return entidad;
    }

    private EntidadAlmacen mapearEntidadAlmacenDesdeDTO(EntidadAlmacen entidadAlmacen, EntidadAlmacenCommandDTO dto){

        if(entidadAlmacen == null){
            entidadAlmacen = modelMapper.map(dto, EntidadAlmacen.class);
        } else {
            modelMapper.map(dto, entidadAlmacen);
        }

        if(entidadAlmacen.getDistrito() != null){
            Distrito distrito = distritoRepository
                    .findById(entidadAlmacen.getDistrito().getId())
                    .orElseThrow(() -> new ValidationException(DistritoResponse.NO_ENCONTRADO));
            entidadAlmacen.setDistrito(distrito);
        }

        if(entidadAlmacen.getEntidadId() != null){
            Entidad entidad = new Entidad();
            entidad.setId(entidadAlmacen.getEntidadId());
            entidadAlmacen.setEntidad(entidad);
        }

        return entidadAlmacen;
    }

    private EntidadCorreo mapearEntidadCorreoDesdeDTO(EntidadCorreo entidadCorreo, EntidadCorreoCommandDTO dto){

        if(entidadCorreo == null){
            entidadCorreo = modelMapper.map(dto, EntidadCorreo.class);
        } else {
            modelMapper.map(dto, entidadCorreo);
        }

        if(entidadCorreo.getEntidadId() != null){
            Entidad entidad = new Entidad();
            entidad.setId(entidadCorreo.getEntidadId());
            entidadCorreo.setEntidad(entidad);
        }

        return entidadCorreo;
    }
}
