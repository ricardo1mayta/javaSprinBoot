package pe.com.avivel.sistemas.guiaselectronicas.service.impl.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.guia_remision.seccion.TransportistaQueryDTO;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.maestros.SerieQueryDTO;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.security.*;
import pe.com.avivel.sistemas.guiaselectronicas.entities.audit.Auditable;
import pe.com.avivel.sistemas.guiaselectronicas.entities.guia_remision.seccion.Transportista;
import pe.com.avivel.sistemas.guiaselectronicas.entities.security.Roles;
import pe.com.avivel.sistemas.guiaselectronicas.entities.security.Usuario;
import pe.com.avivel.sistemas.guiaselectronicas.entities.security.UsuarioSerie;
import pe.com.avivel.sistemas.guiaselectronicas.entities.session.CustomUsernamePasswordAuthenticationToken;
import pe.com.avivel.sistemas.guiaselectronicas.entities.session.DatosSesion;
import pe.com.avivel.sistemas.guiaselectronicas.entities.util.EstadoGeneral;
import pe.com.avivel.sistemas.guiaselectronicas.repository.security.UsuarioRepository;
import pe.com.avivel.sistemas.guiaselectronicas.service.exceptions.ValidationException;
import pe.com.avivel.sistemas.guiaselectronicas.service.spec.maestros.SerieQueryService;
import pe.com.avivel.sistemas.guiaselectronicas.service.spec.security.UsuarioQueryService;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static pe.com.avivel.sistemas.guiaselectronicas.service.impl.ConstansResponse.*;

import static pe.com.avivel.sistemas.guiaselectronicas.entities.security.Roles.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsuarioQueryServiceImpl implements UsuarioQueryService {

    private final UsuarioRepository usuarioRepository;

    private final SerieQueryService serieQueryService;

    private final ModelMapper modelMapper;

    @Override
    public Optional<UsuarioQueryDTO> findById(Integer id) {
        Optional<Usuario> usuarioOp = usuarioRepository.findById(id);
        return usuarioOp.map(u -> modelMapper.map(u, UsuarioQueryDTO.class) );
    }

    @Override
    public Optional<UsuarioQueryDTO> findByUsername(String username) {
        Optional<Usuario> usuarioOp = usuarioRepository.findByUsername(username);
        return usuarioOp.map(u -> modelMapper.map(u, UsuarioQueryDTO.class) );
    }

    @Override
    public Optional<TransportistaQueryDTO> findDatosTransportista() {
        Usuario usuario = findUsuarioActual();
        Transportista transportista = usuario.getDatosTransportista();

        if(transportista == null){
            return Optional.empty();
        }

        return Optional.of(modelMapper.map(transportista, TransportistaQueryDTO.class));
    }

    @Override
    public Optional<DatosSesion> findDatosSesion() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(!(authentication instanceof CustomUsernamePasswordAuthenticationToken)){
            return Optional.empty();
        }
        return   Optional.of (
                    ((CustomUsernamePasswordAuthenticationToken) authentication).getDatosSesion()
        );
    }

    @Override
    public UsuarioQueryDTO findUsuarioQueryActual() {
        Usuario usuario = findUsuarioActual();
        return modelMapper.map(usuario, UsuarioQueryDTO.class);
    }

    @Override
    public Usuario findUsuarioActual() {
        DatosSesion datosLogin = findDatosSesion().orElseThrow(() ->
                new ValidationException(UsuarioResponse.DATOS_SESION_NO_ENCONTRADOS)
        );
        return usuarioRepository.findById(datosLogin.getUsuarioId())
                .orElseThrow(() -> new EntityNotFoundException(UsuarioResponse.NO_ENCONTRADO));
    }

    @Override
    public Page<UsuarioQueryDTO> listAllByFiltrosPaginado(String username, EstadoGeneral estado, Pageable pageable) {
        return usuarioRepository.listAllByFiltrosPaginado(username, estado, pageable)
                .map(u -> modelMapper.map(u, UsuarioQueryDTO.class));
    }

    @Override
    public List<SerieQueryDTO> listSeriesDisponibles() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).collect(Collectors.toList());

        List<String> rolesWithAllSeries = Stream.of(_ADMIN, _CONTABILIDAD, _ALMACEN_JEFATURA)
                .map(Roles::getAuthority).collect(Collectors.toList());

        if(roles.stream().anyMatch(rolesWithAllSeries::contains)){
            return serieQueryService.list();
        }

        return findUsuarioActual()
                .getSeries()
                .stream()
                .map(UsuarioSerie::getSerie)
                .filter(Auditable::isActivo)
                .map(s -> modelMapper.map(s , SerieQueryDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<UsuarioSerieQueryDTO> listAllSeriesAsignadas(Integer usuarioId) {
        return usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntityNotFoundException(UsuarioResponse.NO_ENCONTRADO))
                .getSeries()
                .stream()
                .map(s -> modelMapper.map(s, UsuarioSerieQueryDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<UsuarioArticuloQueryDTO> listAllArticulosAsignados(Integer usuarioId) {
        return usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntityNotFoundException(UsuarioResponse.NO_ENCONTRADO))
                .getArticulos()
                .stream()
                .map(s -> modelMapper.map(s, UsuarioArticuloQueryDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<UsuarioEntidadQueryDTO> listAllEntidadesAsignadas(Integer usuarioId) {
        return usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntityNotFoundException(UsuarioResponse.NO_ENCONTRADO))
                .getEntidades()
                .stream()
                .map(s -> modelMapper.map(s, UsuarioEntidadQueryDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public void validarPermisosUsuarioSobreSerie(String serie) {
        List<String> seriesDisponibles = listSeriesDisponibles()
                .stream()
                .map(SerieQueryDTO::getCodigo)
                .collect(Collectors.toList());

        if(!seriesDisponibles.contains(serie)){
            throw new ValidationException(GuiaRemisionRemitenteResponse.SERIE_NO_DISPONIBLE);
        }
    }
}
