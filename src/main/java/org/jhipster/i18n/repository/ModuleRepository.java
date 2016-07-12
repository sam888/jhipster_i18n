package org.jhipster.i18n.repository;

import org.jhipster.i18n.domain.Module;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Module entity.
 */
@SuppressWarnings("unused")
public interface ModuleRepository extends JpaRepository<Module,Long> {

    Module findByNameIgnoreCase(String moduleName);
}
