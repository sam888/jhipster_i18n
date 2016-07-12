package org.jhipster.i18n.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.jhipster.i18n.domain.ResourceBundle;
import org.jhipster.i18n.repository.ResourceBundleRepository;
import org.jhipster.i18n.repository.search.ResourceBundleSearchRepository;
import org.jhipster.i18n.service.I18nService;
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
 * REST controller for managing ResourceBundle.
 */
@RestController
@RequestMapping("/api")
public class ResourceBundleResource {

    private final Logger log = LoggerFactory.getLogger(ResourceBundleResource.class);
        
    @Inject
    private ResourceBundleRepository resourceBundleRepository;
    
    @Inject
    private ResourceBundleSearchRepository resourceBundleSearchRepository;

    @Inject
    private I18nService i18nService;

    /**
     * POST  /resource-bundles : Create a new resourceBundle.
     *
     * @param resourceBundle the resourceBundle to create
     * @return the ResponseEntity with status 201 (Created) and with body the new resourceBundle, or with status 400 (Bad Request) if the resourceBundle has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/resource-bundles",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ResourceBundle> createResourceBundle(@Valid @RequestBody ResourceBundle resourceBundle) throws URISyntaxException {
        log.debug("REST request to save ResourceBundle : {}", resourceBundle);
        if (resourceBundle.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("resourceBundle", "idexists", "A new resourceBundle cannot already have an ID")).body(null);
        }
        ResourceBundle result = resourceBundleRepository.save(resourceBundle);
        resourceBundleSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/resource-bundles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("resourceBundle", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /resource-bundles : Updates an existing resourceBundle.
     *
     * @param resourceBundle the resourceBundle to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated resourceBundle,
     * or with status 400 (Bad Request) if the resourceBundle is not valid,
     * or with status 500 (Internal Server Error) if the resourceBundle couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/resource-bundles",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ResourceBundle> updateResourceBundle(@Valid @RequestBody ResourceBundle resourceBundle) throws URISyntaxException {
        log.debug("REST request to update ResourceBundle : {}", resourceBundle);
        if (resourceBundle.getId() == null) {
            return createResourceBundle(resourceBundle);
        }
        ResourceBundle result = resourceBundleRepository.save(resourceBundle);
        resourceBundleSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("resourceBundle", resourceBundle.getId().toString()))
            .body(result);
    }

    /**
     * GET  /resource-bundles : get all the resourceBundles.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of resourceBundles in body
     */
    @RequestMapping(value = "/resource-bundles",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<ResourceBundle> getAllResourceBundles() {
        log.debug("REST request to get all ResourceBundles");
        List<ResourceBundle> resourceBundles = resourceBundleRepository.findAll();
        return resourceBundles;
    }

    /**
     * GET  /resource-bundles/:id : get the "id" resourceBundle.
     *
     * @param id the id of the resourceBundle to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the resourceBundle, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/resource-bundles/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ResourceBundle> getResourceBundle(@PathVariable Long id) {
        log.debug("REST request to get ResourceBundle : {}", id);
        ResourceBundle resourceBundle = resourceBundleRepository.findOne(id);
        return Optional.ofNullable(resourceBundle)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /resource-bundles/:id : delete the "id" resourceBundle.
     *
     * @param id the id of the resourceBundle to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/resource-bundles/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteResourceBundle(@PathVariable Long id) {
        log.debug("REST request to delete ResourceBundle : {}", id);
        resourceBundleRepository.delete(id);
        resourceBundleSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("resourceBundle", id.toString())).build();
    }

    /**
     * SEARCH  /_search/resource-bundles?query=:query : search for the resourceBundle corresponding
     * to the query.
     *
     * @param query the query of the resourceBundle search
     * @return the result of the search
     */
    @RequestMapping(value = "/_search/resource-bundles",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<ResourceBundle> searchResourceBundles(@RequestParam String query) {
        log.debug("REST request to search ResourceBundles for query {}", query);
        return StreamSupport
            .stream(resourceBundleSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


    /**
     * POST  /resource-bundles/:id/clear-cache : clear the key-value map cache for the "id" resourceBundle
     *
     * @param resourceBundle   the ResourceBundle to clear key-value map cache
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/resource-bundles/clear-cache",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> clearCache(@RequestBody ResourceBundle resourceBundle) {
        log.debug("REST request to clear cache for ResourceBundle: {}", resourceBundle.getId());

        i18nService.clearCache( resourceBundle );
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("resourceBundle", "CLEAR_CACHE_SUCCESS" )).build();
    }
}
