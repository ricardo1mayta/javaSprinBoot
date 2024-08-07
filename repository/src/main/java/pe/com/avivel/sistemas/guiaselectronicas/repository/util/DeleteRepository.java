package pe.com.avivel.sistemas.guiaselectronicas.repository.util;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;
import pe.com.avivel.sistemas.guiaselectronicas.entities.audit.Auditable;


/**
 * Delete Repository
 * Se sobreescibe el metodo de delete de CrudRepository, ya que el metodo original eliminaba
 * todas las listas asociadas que poseia el objeto.
 */
@NoRepositoryBean
public interface DeleteRepository<E extends Auditable,I> extends JpaRepository<E,I> {

    @Override
    @Modifying
    @Query("UPDATE #{#entityName} E " +
            "SET E.activo = 0, " +
            "E.lastModifiedBy = :#{authentication.name}, " +
            "E.lastModifiedDate = CURRENT_TIMESTAMP " +
            "WHERE E.id = :id")
    void deleteById(@Param("id") I id);
}
