package org.jhipster.i18n.repository;

import org.jhipster.i18n.domain.ResourceBundle;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ResourceBundle entity.
 */
@SuppressWarnings("unused")
public interface ResourceBundleRepository extends JpaRepository<ResourceBundle,Long> {

}
