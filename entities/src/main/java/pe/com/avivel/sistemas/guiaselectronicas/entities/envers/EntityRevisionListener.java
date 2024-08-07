package pe.com.avivel.sistemas.guiaselectronicas.entities.envers;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.envers.RevisionListener;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Slf4j
public class EntityRevisionListener implements RevisionListener {

    @Override
    public void newRevision(Object revisionEntity) {
        UsuarioRevisionEntity usuarioRevisionEntity = (UsuarioRevisionEntity) revisionEntity;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        usuarioRevisionEntity.setUsername(authentication != null ? authentication.getName() : "system");
    }
}

