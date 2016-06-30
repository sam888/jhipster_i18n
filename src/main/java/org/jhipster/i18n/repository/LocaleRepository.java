package org.jhipster.i18n.repository;

import org.jhipster.i18n.domain.Locale;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Locale entity.
 */
@SuppressWarnings("unused")
public interface LocaleRepository extends JpaRepository<Locale,Long> {

}
