package org.jhipster.i18n.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.jhipster.i18n.domain.Locale;
import org.jhipster.i18n.repository.LocaleRepository;
import org.jhipster.i18n.repository.search.LocaleSearchRepository;
import org.jhipster.i18n.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Locale.
 */
@RestController
@RequestMapping("/api")
public class LocaleResource {

    private final Logger log = LoggerFactory.getLogger(LocaleResource.class);
        
    @Inject
    private LocaleRepository localeRepository;
    
    @Inject
    private LocaleSearchRepository localeSearchRepository;
    
    /**
     * POST  /locales : Create a new locale.
     *
     * @param locale the locale to create
     * @return the ResponseEntity with status 201 (Created) and with body the new locale, or with status 400 (Bad Request) if the locale has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/locales",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Locale> createLocale(@Valid @RequestBody Locale locale) throws URISyntaxException {
        log.debug("REST request to save Locale : {}", locale);
        if (locale.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("locale", "idexists", "A new locale cannot already have an ID")).body(null);
        }
        Locale result = localeRepository.save(locale);
        localeSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/locales/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("locale", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /locales : Updates an existing locale.
     *
     * @param locale the locale to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated locale,
     * or with status 400 (Bad Request) if the locale is not valid,
     * or with status 500 (Internal Server Error) if the locale couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/locales",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Locale> updateLocale(@Valid @RequestBody Locale locale) throws URISyntaxException {
        log.debug("REST request to update Locale : {}", locale);
        if (locale.getId() == null) {
            return createLocale(locale);
        }
        Locale result = localeRepository.save(locale);
        localeSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("locale", locale.getId().toString()))
            .body(result);
    }

    /**
     * GET  /locales : get all the locales.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of locales in body
     */
    @RequestMapping(value = "/locales",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Locale> getAllLocales() {
        log.debug("REST request to get all Locales");
        List<Locale> locales = localeRepository.findAll();
        return locales;
    }

    /**
     * GET  /locales/:id : get the "id" locale.
     *
     * @param id the id of the locale to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the locale, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/locales/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Locale> getLocale(@PathVariable Long id) {
        log.debug("REST request to get Locale : {}", id);
        Locale locale = localeRepository.findOne(id);
        return Optional.ofNullable(locale)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /locales/:id : delete the "id" locale.
     *
     * @param id the id of the locale to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/locales/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteLocale(@PathVariable Long id) {
        log.debug("REST request to delete Locale : {}", id);
        localeRepository.delete(id);
        localeSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("locale", id.toString())).build();
    }

    /**
     * SEARCH  /_search/locales?query=:query : search for the locale corresponding
     * to the query.
     *
     * @param query the query of the locale search
     * @return the result of the search
     */
    @RequestMapping(value = "/_search/locales",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Locale> searchLocales(@RequestParam String query) {
        log.debug("REST request to search Locales for query {}", query);
        return StreamSupport
            .stream(localeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


}
