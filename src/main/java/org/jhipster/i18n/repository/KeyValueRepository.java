package org.jhipster.i18n.repository;

import org.jhipster.i18n.domain.KeyValue;

import org.springframework.data.jpa.repository.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Spring Data JPA repository for the KeyValue entity.
 */
@SuppressWarnings("unused")
@Transactional(readOnly = true)
public interface KeyValueRepository extends JpaRepository<KeyValue,Long> {

    @Query( value  = "select kv from KeyValue kv where kv.resourceBundle.id  = ?1")
    List<KeyValue> getKeyValuesByResourceBundleId(Long id);
}
