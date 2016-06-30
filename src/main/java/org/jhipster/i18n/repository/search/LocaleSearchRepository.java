package org.jhipster.i18n.repository.search;

import org.jhipster.i18n.domain.Locale;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Locale entity.
 */
public interface LocaleSearchRepository extends ElasticsearchRepository<Locale, Long> {
}
