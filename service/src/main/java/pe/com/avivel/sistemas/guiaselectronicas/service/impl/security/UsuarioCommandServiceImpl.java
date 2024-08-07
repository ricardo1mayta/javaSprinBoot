package pe.com.avivel.sistemas.guiaselectronicas.service.impl.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.command.guia_remision.seccion.SeccionTransportistaCommandDTO;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.command.security.UsuarioCreateDTO;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.command.security.UsuarioUpdateDTO;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.security.UsuarioArticuloQueryDTO;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.security.UsuarioEntidadQueryDTO;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.security.UsuarioQueryDTO;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.security.UsuarioSerieQueryDTO;
import pe.com.avivel.sistemas.guiaselectronicas.entities.guia_remision.seccion.Transportista;
import pe.com.avivel.sistemas.guiaselectronicas.entities.maestros.Articulo;
import pe.com.avivel.sistemas.guiaselectronicas.entities.maestros.Serie;
import pe.com.avivel.sistemas.guiaselectronicas.entities.maestros.entidad.Entidad;
import pe.com.avivel.sistemas.guiaselectronicas.entities.security.*;
import pe.com.avivel.sistemas.guiaselectronicas.repository.maestros.ArticuloRepository;
import pe.com.avivel.sistemas.guiaselectronicas.repository.maestros.SerieRepository;
import pe.com.avivel.sistemas.guiaselectronicas.repository.maestros.entidad.EntidadRepository;
import pe.com.avivel.sistemas.guiaselectronicas.repository.security.*;
import pe.com.avivel.sistemas.guiaselectronicas.service.exceptions.ValidationException;
import pe.com.avivel.sistemas.guiaselectronicas.service.spec.security.UsuarioCommandService;
import pe.com.avivel.sistemas.guiaselectronicas.service.spec.security.UsuarioQueryService;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static pe.com.avivel.sistemas.guiaselectronicas.service.impl.ConstansResponse.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsuarioCommandServiceImpl implements UsuarioCommandService {

    private final UsuarioRepository usuarioRepository;

    private final SerieRepository serieRepository;

    private final ArticuloRepository articuloRepository;

    private final EntidadRepository entidadRepository;

    private final UsuarioArticuloRepository usuarioArticuloRepository;

    private final UsuarioEntidadRepository usuarioEntidadRepository;

    private final UsuarioSerieRepository usuarioSerieRepository;

    private final RolRepository rolRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final UsuarioQueryService usuarioQueryService;

    private final ModelMapper modelMapper;

    @Override
    public UsuarioQueryDTO create(UsuarioCreateDTO dto) {
        Optional<Usuario> usuarioOpByUsername = usuarioRepository.findByUsername(dto.getUsername());
        Optional<Usuario> usuarioOpByEmail = usuarioRepository.findByEmail(dto.getEmail());

        if(usuarioOpByUsername.isPresent()){
            throw new ValidationException(UsuarioResponse.EXISTE_USERNAME);
        }

        if(usuarioOpByEmail.isPresent()){
            throw new ValidationException(UsuarioResponse.EXISTE_EMAIL);
        }

        Usuario usuario = modelMapper.map(dto, Usuario.class);
        String password = bCryptPasswordEncoder.encode(dto.getPassword().trim());
        usuario.setPassword(password);

        Usuario usuarioNew = usuarioRepository.save(usuario);

        return modelMapper.map(usuarioNew, UsuarioQueryDTO.class);
    }

    @Override
    public UsuarioQueryDTO update(Integer id, UsuarioUpdateDTO dto) {
        Optional<Usuario> usuarioOp = usuarioRepository.findById(id);
        Optional<Usuario> usuarioOpByEmail = usuarioRepository.findByEmail(dto.getEmail());
        Optional<Usuario> usuarioOpByUsername = usuarioRepository.findByUsername(dto.getUsername());


        if(usuarioOp.isEmpty()){
            throw new ValidationException(UsuarioResponse.NO_ENCONTRADO);
        }

        if(usuarioOpByEmail.isPresent() && !usuarioOpByEmail.get().getId().equals(id) ){
            throw new ValidationException(UsuarioResponse.EXISTE_EMAIL);
        }

        if(usuarioOpByUsername.isPresent() && !usuarioOpByUsername.get().getId().equals(id) ){
            throw new ValidationException(UsuarioResponse.EXISTE_USERNAME);
        }

        Usuario usuario = usuarioOp.get();
        modelMapper.map(dto, usuario);

        if(dto.getPassword() != null && !dto.getPassword().isBlank()){
            String password = bCryptPasswordEncoder.encode(dto.getPassword().trim());
            usuario.setPassword(password);
        }

        usuario = usuarioRepository.save(usuario);

        return modelMapper.map(usuario, UsuarioQueryDTO.class);
    }

    @Override
    public void addUsuarioRol(Integer usuarioId, Integer rolId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ValidationException(UsuarioResponse.NO_ENCONTRADO));

        Rol rol = rolRepository.findById(rolId)
                .orElseThrow(() -> new ValidationException(RolResponse.NO_ENCONTRADO));

        if(usuario.getRoles()
                .stream()
                .filter(r -> r.getId().equals(rol.getId()))
                .findFirst().orElse(null) != null){
            throw new ValidationException(YA_EXISTE);
        }

        usuario.getRoles().add(rol);
        usuarioRepository.save(usuario);
    }

    @Override
    public void deleteUsuarioRol(Integer usuarioId, Integer rolId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ValidationException(UsuarioResponse.NO_ENCONTRADO));

        Rol rol = rolRepository.findById(rolId)
                .orElseThrow(() -> new ValidationException(RolResponse.NO_ENCONTRADO));

        if(usuario.getRoles()
                .stream()
                .filter(r -> r.getId().equals(rol.getId()))
                .findFirst().orElse(null) == null){
            throw new ValidationException(NO_ENCONTRADO);
        }

        usuario.getRoles().removeIf(r -> r.getId().equals(rol.getId()));
        usuarioRepository.save(usuario);
    }

    @Override
    public int updatePassword(String oldPassword, String newPassword) {
        UsuarioQueryDTO usuario = usuarioQueryService.findUsuarioQueryActual();

        if(!bCryptPasswordEncoder.matches(oldPassword, usuario.getPassword())){
            throw new ValidationException(UsuarioResponse.BAD_CREDENTIALS);
        }

        String password = bCryptPasswordEncoder.encode(newPassword);
        return usuarioRepository.updatePassword(usuario.getId(),password);
    }

    @Override
    public int setPassword(Integer usuarioId, String password) {
        String newPassword = bCryptPasswordEncoder.encode(password);
        return usuarioRepository.updatePassword(usuarioId, newPassword);
    }

    @Override
    public int updateEstadoById(Integer usuarioId, boolean activo) {
        return usuarioRepository.updateEstadoById(usuarioId, activo);
    }

    @Override
    public UsuarioSerieQueryDTO addUsuarioSerie(Integer usuarioId, Integer serieId) {
        if(usuarioSerieRepository.exists(usuarioId, serieId)){
            throw new ValidationException(YA_EXISTE);
        }

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntityNotFoundException(UsuarioResponse.NO_ENCONTRADO));

        Serie serie = serieRepository.findById(serieId)
                .orElseThrow(() -> new EntityNotFoundException(SerieResponse.NO_ENCONTRADO));

        UsuarioSerie usuarioSerie = new UsuarioSerie(usuario, serie);
        usuarioSerie = usuarioSerieRepository.save(usuarioSerie);
        return modelMapper.map(usuarioSerie, UsuarioSerieQueryDTO.class);
    }

    @Override
    public void deleteUsuarioSerie(Integer usuarioSerieId) {
        UsuarioSerie usuarioSerie = usuarioSerieRepository.findById(usuarioSerieId)
                .orElseThrow(() -> new ValidationException(NO_ENCONTRADO));
        usuarioSerieRepository.deleteById(usuarioSerie.getId());
    }

    @Override
    public UsuarioArticuloQueryDTO addUsuarioArticulo(Integer usuarioId, Integer articuloId) {
        if(usuarioArticuloRepository.exists(usuarioId, articuloId)){
            throw new ValidationException(YA_EXISTE);
        }

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntityNotFoundException(UsuarioResponse.NO_ENCONTRADO));

        Articulo articulo = articuloRepository.findById(articuloId)
                .orElseThrow(() -> new EntityNotFoundException(ArticuloResponse.NO_ENCONTRADO));

        UsuarioArticulo usuarioArticulo = new UsuarioArticulo(usuario, articulo);
        usuarioArticulo = usuarioArticuloRepository.save(usuarioArticulo);
        return modelMapper.map(usuarioArticulo, UsuarioArticuloQueryDTO.class);
    }

    @Override
    public void deleteUsuarioArticulo(Integer usuarioArticuloId) {
        UsuarioArticulo usuarioArticulo = usuarioArticuloRepository.findById(usuarioArticuloId)
                        .orElseThrow(() -> new ValidationException(NO_ENCONTRADO));
        usuarioArticuloRepository.deleteById(usuarioArticulo.getId());
    }

    @Override
    public UsuarioEntidadQueryDTO addUsuarioEntidad(Integer usuarioId, Integer entidadId) {
        if(usuarioEntidadRepository.exists(usuarioId, entidadId)){
            throw new ValidationException(YA_EXISTE);
        }

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntityNotFoundException(UsuarioResponse.NO_ENCONTRADO));

        Entidad entidad = entidadRepository.findById(entidadId)
                .orElseThrow(() -> new EntityNotFoundException(EntidadResponse.NO_ENCONTRADA));

        UsuarioEntidad usuarioEntidad = new UsuarioEntidad(usuario, entidad);
        usuarioEntidad = usuarioEntidadRepository.save(usuarioEntidad);
        return modelMapper.map(usuarioEntidad, UsuarioEntidadQueryDTO.class);
    }

    @Override
    public void deleteUsuarioEntidad(Integer usuarioEntidadId) {
        UsuarioEntidad usuarioEntidad = usuarioEntidadRepository.findById(usuarioEntidadId)
                .orElseThrow(() -> new ValidationException(NO_ENCONTRADO));
        usuarioEntidadRepository.deleteById(usuarioEntidad.getId());
    }

    @Override
    public void updateDatosTransportista(Integer usuarioId, SeccionTransportistaCommandDTO dto) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntityNotFoundException(UsuarioResponse.NO_ENCONTRADO));

        if(!usuario.isUsuarioExterno()){
            throw new ValidationException(UsuarioResponse.REQUIERE_USUARIO_EXTERNO);
        }

        Transportista transportista = modelMapper.map(dto, Transportista.class);
        usuario.setDatosTransportista(transportista);
        usuarioRepository.save(usuario);
    }
}
