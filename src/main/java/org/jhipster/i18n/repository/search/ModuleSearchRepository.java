package org.jhipster.i18n.repository.search;

import org.jhipster.i18n.domain.Module;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Module entity.
 */
public interface ModuleSearchRepository extends ElasticsearchRepository<Module, Long> {
}
