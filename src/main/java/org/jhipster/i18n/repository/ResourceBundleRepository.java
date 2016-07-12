package org.jhipster.i18n.repository;

import org.jhipster.i18n.domain.ResourceBundle;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the ResourceBundle entity.
 */
@SuppressWarnings("unused")
public interface ResourceBundleRepository extends JpaRepository<ResourceBundle,Long> {

    @Query( "select rb from ResourceBundle rb where rb.module.id = :moduleId and rb.locale.id = :localeId")
    ResourceBundle getResourceBundleByModuleIdAndLocaleId( @Param("moduleId") Long moduleId,
                                                           @Param("localeId") Long localeId);
}
