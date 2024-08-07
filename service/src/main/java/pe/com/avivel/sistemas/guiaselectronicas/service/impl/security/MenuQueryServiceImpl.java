package pe.com.avivel.sistemas.guiaselectronicas.service.impl.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.security.MenuQueryDTO;
import pe.com.avivel.sistemas.guiaselectronicas.entities.security.Menu;
import pe.com.avivel.sistemas.guiaselectronicas.repository.security.MenuRepository;
import pe.com.avivel.sistemas.guiaselectronicas.service.spec.security.MenuQueryService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MenuQueryServiceImpl implements MenuQueryService {

    private final MenuRepository menuRepository;

    private final ModelMapper modelMapper;

    @Override
    public Optional<MenuQueryDTO> findById(Integer id) {
        Optional<Menu> menuOp = menuRepository.findById(id);
        return menuOp.map(m -> modelMapper.map(m, MenuQueryDTO.class));
    }

    @Override
    public List<MenuQueryDTO> list() {
        return menuRepository.listParents()
                .stream()
                .map(m -> modelMapper.map(m, MenuQueryDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<MenuQueryDTO> listByUsuarioIdSinJerarquias(Integer usuarioId) {
        return menuRepository.listByUsuarioIdSinJerarquias(usuarioId)
                .stream()
                .map(m -> modelMapper.map(m, MenuQueryDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<MenuQueryDTO> listByUsuarioId(Integer usuarioId) {
        List<Menu> parents = menuRepository.listParentsByUsuarioId(usuarioId);
        for(Menu parent: parents){
            List<Menu> subMenus = menuRepository.listSubMenusByMenuIdAndUsuarioId(parent.getId(), usuarioId);
            parent.setSubMenus(subMenus);
        }

        return parents
                .stream()
                .map(menu -> modelMapper.map(menu, MenuQueryDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<MenuQueryDTO> listByRolIdSinJerarquias(Integer rolId) {
        return menuRepository.listByRolIdSinJerarquias(rolId)
                .stream()
                .map(m -> modelMapper.map(m, MenuQueryDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<MenuQueryDTO> listByRolId(Integer rolId) {
        List<Menu> parents = menuRepository.listParentsByRolId(rolId);

        for(Menu parent: parents){
            List<Menu> subMenus = menuRepository.listSubMenusByMenuIdAndRolId(parent.getId(), rolId);
            parent.setSubMenus(subMenus);
        }

        return parents
                .stream()
                .map(menu -> modelMapper.map(menu, MenuQueryDTO.class))
                .collect(Collectors.toList());
    }
}
