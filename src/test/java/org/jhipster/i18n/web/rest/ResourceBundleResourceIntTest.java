package org.jhipster.i18n.web.rest;

import org.jhipster.i18n.JhipsterI18NApp;
import org.jhipster.i18n.domain.ResourceBundle;
import org.jhipster.i18n.repository.ResourceBundleRepository;
import org.jhipster.i18n.repository.search.ResourceBundleSearchRepository;

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

import org.jhipster.i18n.domain.enumeration.ResourceBundleStatus;

/**
 * Test class for the ResourceBundleResource REST controller.
 *
 * @see ResourceBundleResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = JhipsterI18NApp.class)
@WebAppConfiguration
@IntegrationTest
public class ResourceBundleResourceIntTest {

    private static final String DEFAULT_RESOURCE_BUNDLE_NAME = "AAAAA";
    private static final String UPDATED_RESOURCE_BUNDLE_NAME = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";

    private static final ResourceBundleStatus DEFAULT_STATUS = ResourceBundleStatus.DISABLED;
    private static final ResourceBundleStatus UPDATED_STATUS = ResourceBundleStatus.STATIC_JSON;

    @Inject
    private ResourceBundleRepository resourceBundleRepository;

    @Inject
    private ResourceBundleSearchRepository resourceBundleSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restResourceBundleMockMvc;

    private ResourceBundle resourceBundle;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ResourceBundleResource resourceBundleResource = new ResourceBundleResource();
        ReflectionTestUtils.setField(resourceBundleResource, "resourceBundleSearchRepository", resourceBundleSearchRepository);
        ReflectionTestUtils.setField(resourceBundleResource, "resourceBundleRepository", resourceBundleRepository);
        this.restResourceBundleMockMvc = MockMvcBuilders.standaloneSetup(resourceBundleResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        resourceBundleSearchRepository.deleteAll();
        resourceBundle = new ResourceBundle();
        resourceBundle.setResourceBundleName(DEFAULT_RESOURCE_BUNDLE_NAME);
        resourceBundle.setDescription(DEFAULT_DESCRIPTION);
        resourceBundle.setStatus(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createResourceBundle() throws Exception {
        int databaseSizeBeforeCreate = resourceBundleRepository.findAll().size();

        // Create the ResourceBundle

        restResourceBundleMockMvc.perform(post("/api/resource-bundles")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(resourceBundle)))
                .andExpect(status().isCreated());

        // Validate the ResourceBundle in the database
        List<ResourceBundle> resourceBundles = resourceBundleRepository.findAll();
        assertThat(resourceBundles).hasSize(databaseSizeBeforeCreate + 1);
        ResourceBundle testResourceBundle = resourceBundles.get(resourceBundles.size() - 1);
        assertThat(testResourceBundle.getResourceBundleName()).isEqualTo(DEFAULT_RESOURCE_BUNDLE_NAME);
        assertThat(testResourceBundle.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testResourceBundle.getStatus()).isEqualTo(DEFAULT_STATUS);

        // Validate the ResourceBundle in ElasticSearch
        ResourceBundle resourceBundleEs = resourceBundleSearchRepository.findOne(testResourceBundle.getId());
        assertThat(resourceBundleEs).isEqualToComparingFieldByField(testResourceBundle);
    }

    @Test
    @Transactional
    public void getAllResourceBundles() throws Exception {
        // Initialize the database
        resourceBundleRepository.saveAndFlush(resourceBundle);

        // Get all the resourceBundles
        restResourceBundleMockMvc.perform(get("/api/resource-bundles?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(resourceBundle.getId().intValue())))
                .andExpect(jsonPath("$.[*].resourceBundleName").value(hasItem(DEFAULT_RESOURCE_BUNDLE_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    public void getResourceBundle() throws Exception {
        // Initialize the database
        resourceBundleRepository.saveAndFlush(resourceBundle);

        // Get the resourceBundle
        restResourceBundleMockMvc.perform(get("/api/resource-bundles/{id}", resourceBundle.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(resourceBundle.getId().intValue()))
            .andExpect(jsonPath("$.resourceBundleName").value(DEFAULT_RESOURCE_BUNDLE_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingResourceBundle() throws Exception {
        // Get the resourceBundle
        restResourceBundleMockMvc.perform(get("/api/resource-bundles/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateResourceBundle() throws Exception {
        // Initialize the database
        resourceBundleRepository.saveAndFlush(resourceBundle);
        resourceBundleSearchRepository.save(resourceBundle);
        int databaseSizeBeforeUpdate = resourceBundleRepository.findAll().size();

        // Update the resourceBundle
        ResourceBundle updatedResourceBundle = new ResourceBundle();
        updatedResourceBundle.setId(resourceBundle.getId());
        updatedResourceBundle.setResourceBundleName(UPDATED_RESOURCE_BUNDLE_NAME);
        updatedResourceBundle.setDescription(UPDATED_DESCRIPTION);
        updatedResourceBundle.setStatus(UPDATED_STATUS);

        restResourceBundleMockMvc.perform(put("/api/resource-bundles")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedResourceBundle)))
                .andExpect(status().isOk());

        // Validate the ResourceBundle in the database
        List<ResourceBundle> resourceBundles = resourceBundleRepository.findAll();
        assertThat(resourceBundles).hasSize(databaseSizeBeforeUpdate);
        ResourceBundle testResourceBundle = resourceBundles.get(resourceBundles.size() - 1);
        assertThat(testResourceBundle.getResourceBundleName()).isEqualTo(UPDATED_RESOURCE_BUNDLE_NAME);
        assertThat(testResourceBundle.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testResourceBundle.getStatus()).isEqualTo(UPDATED_STATUS);

        // Validate the ResourceBundle in ElasticSearch
        ResourceBundle resourceBundleEs = resourceBundleSearchRepository.findOne(testResourceBundle.getId());
        assertThat(resourceBundleEs).isEqualToComparingFieldByField(testResourceBundle);
    }

    @Test
    @Transactional
    public void deleteResourceBundle() throws Exception {
        // Initialize the database
        resourceBundleRepository.saveAndFlush(resourceBundle);
        resourceBundleSearchRepository.save(resourceBundle);
        int databaseSizeBeforeDelete = resourceBundleRepository.findAll().size();

        // Get the resourceBundle
        restResourceBundleMockMvc.perform(delete("/api/resource-bundles/{id}", resourceBundle.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean resourceBundleExistsInEs = resourceBundleSearchRepository.exists(resourceBundle.getId());
        assertThat(resourceBundleExistsInEs).isFalse();

        // Validate the database is empty
        List<ResourceBundle> resourceBundles = resourceBundleRepository.findAll();
        assertThat(resourceBundles).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchResourceBundle() throws Exception {
        // Initialize the database
        resourceBundleRepository.saveAndFlush(resourceBundle);
        resourceBundleSearchRepository.save(resourceBundle);

        // Search the resourceBundle
        restResourceBundleMockMvc.perform(get("/api/_search/resource-bundles?query=id:" + resourceBundle.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.[*].id").value(hasItem(resourceBundle.getId().intValue())))
            .andExpect(jsonPath("$.[*].resourceBundleName").value(hasItem(DEFAULT_RESOURCE_BUNDLE_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }
}
