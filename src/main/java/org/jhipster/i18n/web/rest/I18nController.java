package org.jhipster.i18n.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jhipster.i18n.domain.ResourceBundle;
import org.jhipster.i18n.domain.enumeration.ResourceBundleStatus;
import org.jhipster.i18n.service.I18nService;
import org.jhipster.i18n.web.rest.util.UrlUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.Map;


/**
 * @author Samuel Huang
 * Created on 20-Jun-16.
 */
@RestController
@RequestMapping("/i18n")
public class I18nController {

    @Inject
    private I18nService i18nService;

   @RequestMapping(value = "/{languageCode}/{moduleName}.json",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<String> getI18nJson(HttpServletRequest request, @PathVariable String languageCode,
            @PathVariable String moduleName) throws URISyntaxException, MalformedURLException, JsonProcessingException {

       // log.debug("REST request to save ResourceBundle : {}", resourceBundle);

       System.out.println("language code: " + languageCode);
       System.out.println("module name: " + moduleName);

       ResourceBundle resourceBundle = i18nService.getResourceBundle(languageCode, moduleName);
       if ( resourceBundle == null ) return null;

       ResourceBundleStatus resourceBundleStatus = resourceBundle.getStatus();

       if ( resourceBundleStatus == null || resourceBundleStatus == ResourceBundleStatus.DISABLED ) return null;

       if ( resourceBundleStatus == ResourceBundleStatus.STATIC_JSON ) {
           String baseURL = UrlUtil.getBaseURL( request );
           return getStaticJsonResponseEntity(baseURL, languageCode, moduleName);
       }

       if ( resourceBundleStatus == ResourceBundleStatus.DB_JSON ) {
           HttpHeaders responseHeaders = new HttpHeaders();
           Map<String, String> keyValueMap = i18nService.getKeyValueMap(languageCode, moduleName);
           String response = (new ObjectMapper()).writeValueAsString( keyValueMap );
           return new ResponseEntity<String>( response, responseHeaders, HttpStatus.CREATED);
       }

       return null; // should never get here
    }

    private ResponseEntity<String> getStaticJsonResponseEntity(String baseURL, String languageCode, String moduleName) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity( baseURL + "/i18n/json/" + languageCode + "/"
            + moduleName + ".json", String.class);
        return response;
    }

}
