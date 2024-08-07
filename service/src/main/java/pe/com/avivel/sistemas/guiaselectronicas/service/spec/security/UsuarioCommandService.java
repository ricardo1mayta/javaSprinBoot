package pe.com.avivel.sistemas.guiaselectronicas.service.spec.security;

import pe.com.avivel.sistemas.guiaselectronicas.dto.models.command.guia_remision.seccion.SeccionTransportistaCommandDTO;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.command.security.UsuarioCreateDTO;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.command.security.UsuarioUpdateDTO;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.security.UsuarioArticuloQueryDTO;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.security.UsuarioEntidadQueryDTO;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.security.UsuarioQueryDTO;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.security.UsuarioSerieQueryDTO;


public interface UsuarioCommandService {

    UsuarioQueryDTO create(UsuarioCreateDTO dto);

    UsuarioQueryDTO update(Integer id, UsuarioUpdateDTO dto);

    void addUsuarioRol(Integer usuarioId, Integer rolId);

    void deleteUsuarioRol(Integer usuarioId, Integer rolId);

    int updatePassword(String oldPassword, String newPassword);

    int setPassword(Integer usuarioId, String password);

    int updateEstadoById(Integer usuarioId, boolean activo);

    UsuarioSerieQueryDTO addUsuarioSerie(Integer usuarioId, Integer serieId);

    void deleteUsuarioSerie(Integer usuarioSerieId);

    UsuarioArticuloQueryDTO addUsuarioArticulo(Integer usuarioId, Integer articuloId);

    void deleteUsuarioArticulo(Integer usuarioArticuloId);

    UsuarioEntidadQueryDTO addUsuarioEntidad(Integer usuarioId, Integer entidadId);

    void deleteUsuarioEntidad(Integer usuarioEntidadId);

    void updateDatosTransportista(Integer usuarioId, SeccionTransportistaCommandDTO dto);
}
