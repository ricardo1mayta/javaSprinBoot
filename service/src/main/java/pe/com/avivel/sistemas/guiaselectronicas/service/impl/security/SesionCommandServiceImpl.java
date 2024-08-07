package pe.com.avivel.sistemas.guiaselectronicas.service.impl.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.command.security.SesionCommandDTO;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.security.SesionQueryDTO;
import pe.com.avivel.sistemas.guiaselectronicas.entities.security.Sesion;
import pe.com.avivel.sistemas.guiaselectronicas.repository.security.SesionRepository;
import pe.com.avivel.sistemas.guiaselectronicas.repository.security.UsuarioRepository;
import pe.com.avivel.sistemas.guiaselectronicas.service.exceptions.DeleteEntityException;
import pe.com.avivel.sistemas.guiaselectronicas.service.spec.security.SesionCommandService;

import java.util.Date;
import java.util.UUID;

import static pe.com.avivel.sistemas.guiaselectronicas.service.impl.ConstansResponse.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class SesionCommandServiceImpl implements SesionCommandService {

    @Value("${app.token.expires-in-miliseconds}")
    private Long EXPIRES_IN_MILLISECOND;

    private final SesionRepository sesionRepository;

    private final UsuarioRepository usuarioRepository;

    private final ModelMapper modelMapper;

    @Override
    public SesionQueryDTO create(SesionCommandDTO sesionCommandDTO) {
        Sesion sesion = modelMapper.map(sesionCommandDTO, Sesion.class);
        sesion.setToken(UUID.randomUUID().toString());
        sesion.setTokenCreacion(new Date(System.currentTimeMillis()));
        sesion.setTokenExpiracion(new Date(System.currentTimeMillis() + EXPIRES_IN_MILLISECOND));
        Sesion sesionNew = sesionRepository.save(sesion);
        usuarioRepository.updateLastLogin(sesionNew.getUsuario().getUsername());
        return modelMapper.map(sesionNew, SesionQueryDTO.class);
    }

    @Override
    public void deleteById(Integer id) {
        Sesion sesion = sesionRepository.findById(id)
                .orElseThrow(() -> new DeleteEntityException(SesionResponse.NO_ENCONTRADA));
        sesion.setActivo(false);
        sesionRepository.save(sesion);
    }
}
