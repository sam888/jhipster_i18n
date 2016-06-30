package org.jhipster.i18n.web.rest;

import org.jhipster.i18n.JhipsterI18NApp;
import org.jhipster.i18n.domain.KeyValue;
import org.jhipster.i18n.repository.KeyValueRepository;
import org.jhipster.i18n.repository.search.KeyValueSearchRepository;

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
 * Test class for the KeyValueResource REST controller.
 *
 * @see KeyValueResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = JhipsterI18NApp.class)
@WebAppConfiguration
@IntegrationTest
public class KeyValueResourceIntTest {

    private static final String DEFAULT_PROPERTY = "AAAAA";
    private static final String UPDATED_PROPERTY = "BBBBB";
    private static final String DEFAULT_PROPERTY_VALUE = "AAAAA";
    private static final String UPDATED_PROPERTY_VALUE = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    @Inject
    private KeyValueRepository keyValueRepository;

    @Inject
    private KeyValueSearchRepository keyValueSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restKeyValueMockMvc;

    private KeyValue keyValue;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        KeyValueResource keyValueResource = new KeyValueResource();
        ReflectionTestUtils.setField(keyValueResource, "keyValueSearchRepository", keyValueSearchRepository);
        ReflectionTestUtils.setField(keyValueResource, "keyValueRepository", keyValueRepository);
        this.restKeyValueMockMvc = MockMvcBuilders.standaloneSetup(keyValueResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        keyValueSearchRepository.deleteAll();
        keyValue = new KeyValue();
        keyValue.setProperty(DEFAULT_PROPERTY);
        keyValue.setPropertyValue(DEFAULT_PROPERTY_VALUE);
        keyValue.setDescription(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createKeyValue() throws Exception {
        int databaseSizeBeforeCreate = keyValueRepository.findAll().size();

        // Create the KeyValue

        restKeyValueMockMvc.perform(post("/api/key-values")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(keyValue)))
                .andExpect(status().isCreated());

        // Validate the KeyValue in the database
        List<KeyValue> keyValues = keyValueRepository.findAll();
        assertThat(keyValues).hasSize(databaseSizeBeforeCreate + 1);
        KeyValue testKeyValue = keyValues.get(keyValues.size() - 1);
        assertThat(testKeyValue.getProperty()).isEqualTo(DEFAULT_PROPERTY);
        assertThat(testKeyValue.getPropertyValue()).isEqualTo(DEFAULT_PROPERTY_VALUE);
        assertThat(testKeyValue.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);

        // Validate the KeyValue in ElasticSearch
        KeyValue keyValueEs = keyValueSearchRepository.findOne(testKeyValue.getId());
        assertThat(keyValueEs).isEqualToComparingFieldByField(testKeyValue);
    }

    @Test
    @Transactional
    public void checkPropertyIsRequired() throws Exception {
        int databaseSizeBeforeTest = keyValueRepository.findAll().size();
        // set the field null
        keyValue.setProperty(null);

        // Create the KeyValue, which fails.

        restKeyValueMockMvc.perform(post("/api/key-values")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(keyValue)))
                .andExpect(status().isBadRequest());

        List<KeyValue> keyValues = keyValueRepository.findAll();
        assertThat(keyValues).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPropertyValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = keyValueRepository.findAll().size();
        // set the field null
        keyValue.setPropertyValue(null);

        // Create the KeyValue, which fails.

        restKeyValueMockMvc.perform(post("/api/key-values")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(keyValue)))
                .andExpect(status().isBadRequest());

        List<KeyValue> keyValues = keyValueRepository.findAll();
        assertThat(keyValues).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllKeyValues() throws Exception {
        // Initialize the database
        keyValueRepository.saveAndFlush(keyValue);

        // Get all the keyValues
        restKeyValueMockMvc.perform(get("/api/key-values?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(keyValue.getId().intValue())))
                .andExpect(jsonPath("$.[*].property").value(hasItem(DEFAULT_PROPERTY.toString())))
                .andExpect(jsonPath("$.[*].propertyValue").value(hasItem(DEFAULT_PROPERTY_VALUE.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getKeyValue() throws Exception {
        // Initialize the database
        keyValueRepository.saveAndFlush(keyValue);

        // Get the keyValue
        restKeyValueMockMvc.perform(get("/api/key-values/{id}", keyValue.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(keyValue.getId().intValue()))
            .andExpect(jsonPath("$.property").value(DEFAULT_PROPERTY.toString()))
            .andExpect(jsonPath("$.propertyValue").value(DEFAULT_PROPERTY_VALUE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingKeyValue() throws Exception {
        // Get the keyValue
        restKeyValueMockMvc.perform(get("/api/key-values/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateKeyValue() throws Exception {
        // Initialize the database
        keyValueRepository.saveAndFlush(keyValue);
        keyValueSearchRepository.save(keyValue);
        int databaseSizeBeforeUpdate = keyValueRepository.findAll().size();

        // Update the keyValue
        KeyValue updatedKeyValue = new KeyValue();
        updatedKeyValue.setId(keyValue.getId());
        updatedKeyValue.setProperty(UPDATED_PROPERTY);
        updatedKeyValue.setPropertyValue(UPDATED_PROPERTY_VALUE);
        updatedKeyValue.setDescription(UPDATED_DESCRIPTION);

        restKeyValueMockMvc.perform(put("/api/key-values")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedKeyValue)))
                .andExpect(status().isOk());

        // Validate the KeyValue in the database
        List<KeyValue> keyValues = keyValueRepository.findAll();
        assertThat(keyValues).hasSize(databaseSizeBeforeUpdate);
        KeyValue testKeyValue = keyValues.get(keyValues.size() - 1);
        assertThat(testKeyValue.getProperty()).isEqualTo(UPDATED_PROPERTY);
        assertThat(testKeyValue.getPropertyValue()).isEqualTo(UPDATED_PROPERTY_VALUE);
        assertThat(testKeyValue.getDescription()).isEqualTo(UPDATED_DESCRIPTION);

        // Validate the KeyValue in ElasticSearch
        KeyValue keyValueEs = keyValueSearchRepository.findOne(testKeyValue.getId());
        assertThat(keyValueEs).isEqualToComparingFieldByField(testKeyValue);
    }

    @Test
    @Transactional
    public void deleteKeyValue() throws Exception {
        // Initialize the database
        keyValueRepository.saveAndFlush(keyValue);
        keyValueSearchRepository.save(keyValue);
        int databaseSizeBeforeDelete = keyValueRepository.findAll().size();

        // Get the keyValue
        restKeyValueMockMvc.perform(delete("/api/key-values/{id}", keyValue.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean keyValueExistsInEs = keyValueSearchRepository.exists(keyValue.getId());
        assertThat(keyValueExistsInEs).isFalse();

        // Validate the database is empty
        List<KeyValue> keyValues = keyValueRepository.findAll();
        assertThat(keyValues).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchKeyValue() throws Exception {
        // Initialize the database
        keyValueRepository.saveAndFlush(keyValue);
        keyValueSearchRepository.save(keyValue);

        // Search the keyValue
        restKeyValueMockMvc.perform(get("/api/_search/key-values?query=id:" + keyValue.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.[*].id").value(hasItem(keyValue.getId().intValue())))
            .andExpect(jsonPath("$.[*].property").value(hasItem(DEFAULT_PROPERTY.toString())))
            .andExpect(jsonPath("$.[*].propertyValue").value(hasItem(DEFAULT_PROPERTY_VALUE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }
}
