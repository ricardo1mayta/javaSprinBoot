package pe.com.avivel.sistemas.guiaselectronicas.service.spec.security;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.guia_remision.seccion.TransportistaQueryDTO;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.maestros.SerieQueryDTO;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.security.*;
import pe.com.avivel.sistemas.guiaselectronicas.entities.security.Usuario;
import pe.com.avivel.sistemas.guiaselectronicas.entities.session.DatosSesion;
import pe.com.avivel.sistemas.guiaselectronicas.entities.util.EstadoGeneral;

import java.util.List;
import java.util.Optional;

public interface UsuarioQueryService {

    Optional<UsuarioQueryDTO> findById(Integer id);

    Optional<UsuarioQueryDTO> findByUsername(String username);

    Optional<TransportistaQueryDTO> findDatosTransportista();

    Optional<DatosSesion> findDatosSesion();

    UsuarioQueryDTO findUsuarioQueryActual();

    Usuario findUsuarioActual();

    Page<UsuarioQueryDTO> listAllByFiltrosPaginado(String username, EstadoGeneral estado,
                                                   Pageable pageable);

    List<SerieQueryDTO> listSeriesDisponibles();

    List<UsuarioSerieQueryDTO> listAllSeriesAsignadas(Integer usuarioId);

    List<UsuarioArticuloQueryDTO> listAllArticulosAsignados(Integer usuarioId);

    List<UsuarioEntidadQueryDTO> listAllEntidadesAsignadas(Integer usuarioId);

    void validarPermisosUsuarioSobreSerie(String serie);
}
