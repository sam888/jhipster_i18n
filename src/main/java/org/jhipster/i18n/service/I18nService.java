package org.jhipster.i18n.service;

import org.jhipster.i18n.domain.KeyValue;
import org.jhipster.i18n.domain.Locale;
import org.jhipster.i18n.domain.Module;
import org.jhipster.i18n.domain.ResourceBundle;
import org.jhipster.i18n.repository.KeyValueRepository;
import org.jhipster.i18n.repository.LocaleRepository;
import org.jhipster.i18n.repository.ModuleRepository;
import org.jhipster.i18n.repository.ResourceBundleRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * @author Samuel Huang
 * Created on 21-Jun-16
 */
@Service
public class I18nService {


    @Inject
    private LocaleRepository localeRepository;

    @Inject
    private ModuleRepository moduleRepository;

    @Inject
    private ResourceBundleRepository resourceBundleRepository;

    @Inject
    private KeyValueRepository keyValueRepository;

    /**
     * A map of < 'language Code', Map<moduleName, Map<key, value>> >. If country code is required in i18n, the map
     * will be (Locale, Map(moduleName, Map(key, value)) ) since Locale contains both language & country codes.
     * Assume country code is not used now.
     *
     * ConcurrentHashMap is used since it is a static field providing concurrent access to multiple threads and we want it to be thread safe.
     */
    private static final Map<String, Map<String, Map<String, String>>> I18N_DATA_MAP = new ConcurrentHashMap<String, Map<String, Map<String, String>>>();

    public Map<String, String> getKeyValueMap(String languageCode, String moduleName) {

        Map<String, String> keyValueMap = null;
        if ( ! isResourceBundleRegistered( languageCode, moduleName ) ) {

            keyValueMap = getKeyValueMapFromDB( languageCode, moduleName ); // This will be null if no data from DB exits.
            cacheKeyValueMap( languageCode, moduleName, keyValueMap );      // Not supposed to cache keyValueMap if it's null.

        } else {

            Map<String, Map<String, String>> moduleNameToKeyValueMap = I18N_DATA_MAP.get( languageCode );
            if ( moduleNameToKeyValueMap != null ) {
                keyValueMap = moduleNameToKeyValueMap.get( moduleName );
            } else {
                // if check using isResourceBundleRegistered(..) above should have ruled this scenario out already
                throw new IllegalStateException("I18N_DATA_MAP contains key entry for '" + languageCode + "' but no " +
                        "actual data. This should never happen!");
            }

        }

        if ( keyValueMap  == null ) {   // return empty map if null to prevent NPE
             keyValueMap = new HashMap<String, String>();
        }

        // Copy map data since we want to return a defensive copy so caller can mess with returned map all they want without
        // breaking the cached map.
        //
        // Map.clone() not used to copy map data as the use of clone() is not advised as explained by Josh Bloch in
        // http://www.artima.com/intv/bloch13.html
        Map<String, String> keyValueMapToReturn = new HashMap<String, String>();
        keyValueMapToReturn.putAll( keyValueMap );
        return keyValueMapToReturn;
    }

    public boolean isResourceBundleRegistered(String languageCode, String moduleName) {
        Map<String, Map<String, String>> moduleNameToKeyValueMap = I18N_DATA_MAP.get( languageCode );
        if ( moduleNameToKeyValueMap == null ) {
            return false;
        }
        Map<String, String> keyValueMap = moduleNameToKeyValueMap.get( moduleName );
        return (keyValueMap != null);
    }

    public Map<String, String> getKeyValueMapFromDB(String languageCode, String moduleName) {

        List<Locale> localeList = localeRepository.findByLanguageCode( languageCode );

        Locale locale = null;
        if ( localeList.size() > 0 ) {
            locale = localeList.get(0);
        } else {
            return null;
        }

        Module module = moduleRepository.findByNameIgnoreCase(moduleName);
        if ( module == null ) return null;

        ResourceBundle resourceBundle = resourceBundleRepository.getResourceBundleByModuleIdAndLocaleId(module.getId(), locale.getId());
        if ( resourceBundle == null ) return null;

        List<KeyValue> keyValueList = keyValueRepository.getKeyValuesByResourceBundleId( resourceBundle.getId() );

        Map<String, String> keyValueMap = new HashMap<String, String>();
        for ( KeyValue keyValue: keyValueList ) {
            keyValueMap.put( keyValue.getProperty(), keyValue.getPropertyValue() );
        }
        return keyValueMap;
    }

    /**
     * Get ResourceBundle by language Code and module name
     * @param languageCode
     * @param moduleName
     * @return
     */
    public ResourceBundle getResourceBundle(String languageCode, String moduleName) {

        List<Locale> localeList = localeRepository.findByLanguageCode( languageCode );

        Locale locale = null;
        if ( localeList.size() > 0 ) {
            locale = localeList.get(0);
        } else {
            return null;
        }

        Module module = moduleRepository.findByNameIgnoreCase(moduleName);
        if ( module == null ) return null;

        return resourceBundleRepository.getResourceBundleByModuleIdAndLocaleId(module.getId(), locale.getId());
    }

    public void cacheKeyValueMap(String languageCode, String moduleName, Map<String, String> keyValueMap) {

        if ( keyValueMap == null ) return; // Do not cache any data if keyValueMap is null!
        if ( languageCode == null || moduleName == null ) return;

        Map<String, Map<String, String>> moduleNameToKeyValueMap = I18N_DATA_MAP.get( languageCode );
        if ( moduleNameToKeyValueMap == null ) {

            moduleNameToKeyValueMap = new HashMap<String, Map<String, String>>();
            moduleNameToKeyValueMap.put( moduleName, keyValueMap );
            I18N_DATA_MAP.put( languageCode, moduleNameToKeyValueMap );

        } else {

            moduleNameToKeyValueMap.put( moduleName, keyValueMap );
        }
    }

    public void clearCache(ResourceBundle resourceBundle) {

        if ( resourceBundle == null ) {
            return;
        }

        Locale locale = resourceBundle.getLocale();
        if ( locale == null ) return;
        String languageCode = locale.getLanguageCode();

        Module module = resourceBundle.getModule();
        String moduleName = module.getName();

        Map<String, Map<String, String>> moduleNameToKeyValueMap = I18N_DATA_MAP.get( languageCode );
        if ( moduleNameToKeyValueMap == null ) return;

        Map<String, String> keyValueMap = moduleNameToKeyValueMap.get( moduleName );
        if ( keyValueMap == null ) return; // already empty, nothing to clear

        moduleNameToKeyValueMap.put( moduleName, null );
    }

}
