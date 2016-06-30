package org.jhipster.i18n.repository.search;

import org.jhipster.i18n.domain.KeyValue;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the KeyValue entity.
 */
public interface KeyValueSearchRepository extends ElasticsearchRepository<KeyValue, Long> {
}
