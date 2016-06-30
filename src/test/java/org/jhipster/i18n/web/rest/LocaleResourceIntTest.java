package org.jhipster.i18n.web.rest;

import org.jhipster.i18n.JhipsterI18NApp;
import org.jhipster.i18n.domain.Locale;
import org.jhipster.i18n.repository.LocaleRepository;
import org.jhipster.i18n.repository.search.LocaleSearchRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the LocaleResource REST controller.
 *
 * @see LocaleResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = JhipsterI18NApp.class)
@WebAppConfiguration
@IntegrationTest
public class LocaleResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_LANGUAGE_CODE = "AAAAA";
    private static final String UPDATED_LANGUAGE_CODE = "BBBBB";
    private static final String DEFAULT_COUNTRY_CODE = "AAAAA";
    private static final String UPDATED_COUNTRY_CODE = "BBBBB";

    @Inject
    private LocaleRepository localeRepository;

    @Inject
    private LocaleSearchRepository localeSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restLocaleMockMvc;

    private Locale locale;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        LocaleResource localeResource = new LocaleResource();
        ReflectionTestUtils.setField(localeResource, "localeSearchRepository", localeSearchRepository);
        ReflectionTestUtils.setField(localeResource, "localeRepository", localeRepository);
        this.restLocaleMockMvc = MockMvcBuilders.standaloneSetup(localeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        localeSearchRepository.deleteAll();
        locale = new Locale();
        locale.setName(DEFAULT_NAME);
        locale.setLanguageCode(DEFAULT_LANGUAGE_CODE);
        locale.setCountryCode(DEFAULT_COUNTRY_CODE);
    }

    @Test
    @Transactional
    public void createLocale() throws Exception {
        int databaseSizeBeforeCreate = localeRepository.findAll().size();

        // Create the Locale

        restLocaleMockMvc.perform(post("/api/locales")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(locale)))
                .andExpect(status().isCreated());

        // Validate the Locale in the database
        List<Locale> locales = localeRepository.findAll();
        assertThat(locales).hasSize(databaseSizeBeforeCreate + 1);
        Locale testLocale = locales.get(locales.size() - 1);
        assertThat(testLocale.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testLocale.getLanguageCode()).isEqualTo(DEFAULT_LANGUAGE_CODE);
        assertThat(testLocale.getCountryCode()).isEqualTo(DEFAULT_COUNTRY_CODE);

        // Validate the Locale in ElasticSearch
        Locale localeEs = localeSearchRepository.findOne(testLocale.getId());
        assertThat(localeEs).isEqualToComparingFieldByField(testLocale);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = localeRepository.findAll().size();
        // set the field null
        locale.setName(null);

        // Create the Locale, which fails.

        restLocaleMockMvc.perform(post("/api/locales")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(locale)))
                .andExpect(status().isBadRequest());

        List<Locale> locales = localeRepository.findAll();
        assertThat(locales).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLanguageCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = localeRepository.findAll().size();
        // set the field null
        locale.setLanguageCode(null);

        // Create the Locale, which fails.

        restLocaleMockMvc.perform(post("/api/locales")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(locale)))
                .andExpect(status().isBadRequest());

        List<Locale> locales = localeRepository.findAll();
        assertThat(locales).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLocales() throws Exception {
        // Initialize the database
        localeRepository.saveAndFlush(locale);

        // Get all the locales
        restLocaleMockMvc.perform(get("/api/locales?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(locale.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].languageCode").value(hasItem(DEFAULT_LANGUAGE_CODE.toString())))
                .andExpect(jsonPath("$.[*].countryCode").value(hasItem(DEFAULT_COUNTRY_CODE.toString())));
    }

    @Test
    @Transactional
    public void getLocale() throws Exception {
        // Initialize the database
        localeRepository.saveAndFlush(locale);

        // Get the locale
        restLocaleMockMvc.perform(get("/api/locales/{id}", locale.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(locale.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.languageCode").value(DEFAULT_LANGUAGE_CODE.toString()))
            .andExpect(jsonPath("$.countryCode").value(DEFAULT_COUNTRY_CODE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLocale() throws Exception {
        // Get the locale
        restLocaleMockMvc.perform(get("/api/locales/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLocale() throws Exception {
        // Initialize the database
        localeRepository.saveAndFlush(locale);
        localeSearchRepository.save(locale);
        int databaseSizeBeforeUpdate = localeRepository.findAll().size();

        // Update the locale
        Locale updatedLocale = new Locale();
        updatedLocale.setId(locale.getId());
        updatedLocale.setName(UPDATED_NAME);
        updatedLocale.setLanguageCode(UPDATED_LANGUAGE_CODE);
        updatedLocale.setCountryCode(UPDATED_COUNTRY_CODE);

        restLocaleMockMvc.perform(put("/api/locales")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedLocale)))
                .andExpect(status().isOk());

        // Validate the Locale in the database
        List<Locale> locales = localeRepository.findAll();
        assertThat(locales).hasSize(databaseSizeBeforeUpdate);
        Locale testLocale = locales.get(locales.size() - 1);
        assertThat(testLocale.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testLocale.getLanguageCode()).isEqualTo(UPDATED_LANGUAGE_CODE);
        assertThat(testLocale.getCountryCode()).isEqualTo(UPDATED_COUNTRY_CODE);

        // Validate the Locale in ElasticSearch
        Locale localeEs = localeSearchRepository.findOne(testLocale.getId());
        assertThat(localeEs).isEqualToComparingFieldByField(testLocale);
    }

    @Test
    @Transactional
    public void deleteLocale() throws Exception {
        // Initialize the database
        localeRepository.saveAndFlush(locale);
        localeSearchRepository.save(locale);
        int databaseSizeBeforeDelete = localeRepository.findAll().size();

        // Get the locale
        restLocaleMockMvc.perform(delete("/api/locales/{id}", locale.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean localeExistsInEs = localeSearchRepository.exists(locale.getId());
        assertThat(localeExistsInEs).isFalse();

        // Validate the database is empty
        List<Locale> locales = localeRepository.findAll();
        assertThat(locales).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchLocale() throws Exception {
        // Initialize the database
        localeRepository.saveAndFlush(locale);
        localeSearchRepository.save(locale);

        // Search the locale
        restLocaleMockMvc.perform(get("/api/_search/locales?query=id:" + locale.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.[*].id").value(hasItem(locale.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].languageCode").value(hasItem(DEFAULT_LANGUAGE_CODE.toString())))
            .andExpect(jsonPath("$.[*].countryCode").value(hasItem(DEFAULT_COUNTRY_CODE.toString())));
    }
}
