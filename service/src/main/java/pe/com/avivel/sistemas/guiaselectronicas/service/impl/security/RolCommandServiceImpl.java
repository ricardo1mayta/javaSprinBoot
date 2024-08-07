package pe.com.avivel.sistemas.guiaselectronicas.service.impl.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pe.com.avivel.sistemas.guiaselectronicas.entities.security.Rol;
import pe.com.avivel.sistemas.guiaselectronicas.repository.security.RolRepository;
import pe.com.avivel.sistemas.guiaselectronicas.service.exceptions.ValidationException;
import pe.com.avivel.sistemas.guiaselectronicas.service.spec.security.RolCommandService;

import java.util.List;

import static pe.com.avivel.sistemas.guiaselectronicas.service.impl.ConstansResponse.*;
import static pe.com.avivel.sistemas.guiaselectronicas.entities.security.RolTipo.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class RolCommandServiceImpl implements RolCommandService {

    private final RolRepository rolRepository;

    @Override
    public void createRolMenus(Integer rolId, List<Integer> menusIds) {
        Rol rol = rolRepository.findById(rolId)
                .orElseThrow(() -> new ValidationException(RolResponse.NO_ENCONTRADO));

        if(rol.getTipo() != M){
            throw new ValidationException(RolResponse.DEBE_SER_TIPO_MENU);
        }

        rolRepository.deleteRolMenusByRolId(rolId);

        if (!menusIds.isEmpty()) {
            menusIds.forEach(mi -> rolRepository.insertRolMenu(rolId, mi));
        }
    }
}
