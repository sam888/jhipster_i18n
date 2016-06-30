package org.jhipster.i18n.repository.search;

import org.jhipster.i18n.domain.ResourceBundle;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the ResourceBundle entity.
 */
public interface ResourceBundleSearchRepository extends ElasticsearchRepository<ResourceBundle, Long> {
}
