package pe.com.avivel.sistemas.guiaselectronicas.service.spec.security;

import java.util.List;

public interface RolCommandService {
    void createRolMenus(Integer rolId, List<Integer> menusIds);
}
