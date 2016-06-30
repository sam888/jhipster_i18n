package org.jhipster.i18n.repository;

import org.jhipster.i18n.domain.KeyValue;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the KeyValue entity.
 */
@SuppressWarnings("unused")
public interface KeyValueRepository extends JpaRepository<KeyValue,Long> {

}
