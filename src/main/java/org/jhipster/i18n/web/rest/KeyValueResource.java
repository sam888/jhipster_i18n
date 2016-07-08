package org.jhipster.i18n.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.jhipster.i18n.domain.KeyValue;
import org.jhipster.i18n.repository.KeyValueRepository;
import org.jhipster.i18n.repository.search.KeyValueSearchRepository;
import org.jhipster.i18n.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
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
 * REST controller for managing KeyValue.
 */
@RestController
@RequestMapping("/api")
public class KeyValueResource {

    private final Logger log = LoggerFactory.getLogger(KeyValueResource.class);
        
    @Inject
    private KeyValueRepository keyValueRepository;
    
    @Inject
    private KeyValueSearchRepository keyValueSearchRepository;
    
    /**
     * POST  /key-values : Create a new keyValue.
     *
     * @param keyValue the keyValue to create
     * @return the ResponseEntity with status 201 (Created) and with body the new keyValue, or with status 400 (Bad Request) if the keyValue has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/key-values",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional @Modifying
    public ResponseEntity<KeyValue> createKeyValue(@Valid @RequestBody KeyValue keyValue) throws URISyntaxException {
        log.debug("REST request to save KeyValue : {}", keyValue);
        if (keyValue.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("keyValue", "idexists", "A new keyValue cannot already have an ID")).body(null);
        }
        KeyValue result = keyValueRepository.save(keyValue);
        keyValueSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/key-values/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("keyValue", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /key-values : Updates an existing keyValue.
     *
     * @param keyValue the keyValue to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated keyValue,
     * or with status 400 (Bad Request) if the keyValue is not valid,
     * or with status 500 (Internal Server Error) if the keyValue couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/key-values",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional @Modifying
    public ResponseEntity<KeyValue> updateKeyValue(@Valid @RequestBody KeyValue keyValue) throws URISyntaxException {
        log.debug("REST request to update KeyValue : {}", keyValue);
        if (keyValue.getId() == null) {
            return createKeyValue(keyValue);
        }
        KeyValue result = keyValueRepository.save(keyValue);
        keyValueSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("keyValue", keyValue.getId().toString()))
            .body(result);
    }

    /**
     * GET  /key-values : get all the keyValues.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of keyValues in body
     */
    @RequestMapping(value = "/key-values",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<KeyValue> getAllKeyValues() {
        log.debug("REST request to get all KeyValues");
        List<KeyValue> keyValues = keyValueRepository.findAll();
        return keyValues;
    }

    /**
     * GET  /key-values/:id : get the "id" keyValue.
     *
     * @param id the id of the keyValue to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the keyValue, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/key-values/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<KeyValue> getKeyValue(@PathVariable Long id) {
        log.debug("REST request to get KeyValue : {}", id);
        KeyValue keyValue = keyValueRepository.findOne(id);
        return Optional.ofNullable(keyValue)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /key-values/:id : delete the "id" keyValue.
     *
     * @param id the id of the keyValue to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/key-values/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional @Modifying
    public ResponseEntity<Void> deleteKeyValue(@PathVariable Long id) {
        log.debug("REST request to delete KeyValue : {}", id);
        keyValueRepository.delete(id);
        keyValueSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("keyValue", id.toString())).build();
    }

    /**
     * SEARCH  /_search/key-values?query=:query : search for the keyValue corresponding
     * to the query.
     *
     * @param query the query of the keyValue search
     * @return the result of the search
     */
    @RequestMapping(value = "/_search/key-values",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<KeyValue> searchKeyValues(@RequestParam String query) {
        log.debug("REST request to search KeyValues for query {}", query);
        return StreamSupport
            .stream(keyValueSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


    @Timed
    @RequestMapping(value = "/key-values/rb/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<KeyValue> getKeyValuesByResourceBundleId(@PathVariable Long id) {
        log.debug("REST request to get all KeyValues by Resource Bundle Id");
        List<KeyValue> keyValues = keyValueRepository.getKeyValuesByResourceBundleId( id );
        return keyValues;
    }
}
