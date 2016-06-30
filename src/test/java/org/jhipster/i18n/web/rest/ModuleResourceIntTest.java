package org.jhipster.i18n.web.rest;

import org.jhipster.i18n.JhipsterI18NApp;
import org.jhipster.i18n.domain.Module;
import org.jhipster.i18n.repository.ModuleRepository;
import org.jhipster.i18n.repository.search.ModuleSearchRepository;

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
 * Test class for the ModuleResource REST controller.
 *
 * @see ModuleResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = JhipsterI18NApp.class)
@WebAppConfiguration
@IntegrationTest
public class ModuleResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    @Inject
    private ModuleRepository moduleRepository;

    @Inject
    private ModuleSearchRepository moduleSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restModuleMockMvc;

    private Module module;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ModuleResource moduleResource = new ModuleResource();
        ReflectionTestUtils.setField(moduleResource, "moduleSearchRepository", moduleSearchRepository);
        ReflectionTestUtils.setField(moduleResource, "moduleRepository", moduleRepository);
        this.restModuleMockMvc = MockMvcBuilders.standaloneSetup(moduleResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        moduleSearchRepository.deleteAll();
        module = new Module();
        module.setName(DEFAULT_NAME);
        module.setDescription(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createModule() throws Exception {
        int databaseSizeBeforeCreate = moduleRepository.findAll().size();

        // Create the Module

        restModuleMockMvc.perform(post("/api/modules")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(module)))
                .andExpect(status().isCreated());

        // Validate the Module in the database
        List<Module> modules = moduleRepository.findAll();
        assertThat(modules).hasSize(databaseSizeBeforeCreate + 1);
        Module testModule = modules.get(modules.size() - 1);
        assertThat(testModule.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testModule.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);

        // Validate the Module in ElasticSearch
        Module moduleEs = moduleSearchRepository.findOne(testModule.getId());
        assertThat(moduleEs).isEqualToComparingFieldByField(testModule);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = moduleRepository.findAll().size();
        // set the field null
        module.setName(null);

        // Create the Module, which fails.

        restModuleMockMvc.perform(post("/api/modules")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(module)))
                .andExpect(status().isBadRequest());

        List<Module> modules = moduleRepository.findAll();
        assertThat(modules).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllModules() throws Exception {
        // Initialize the database
        moduleRepository.saveAndFlush(module);

        // Get all the modules
        restModuleMockMvc.perform(get("/api/modules?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(module.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getModule() throws Exception {
        // Initialize the database
        moduleRepository.saveAndFlush(module);

        // Get the module
        restModuleMockMvc.perform(get("/api/modules/{id}", module.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(module.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingModule() throws Exception {
        // Get the module
        restModuleMockMvc.perform(get("/api/modules/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateModule() throws Exception {
        // Initialize the database
        moduleRepository.saveAndFlush(module);
        moduleSearchRepository.save(module);
        int databaseSizeBeforeUpdate = moduleRepository.findAll().size();

        // Update the module
        Module updatedModule = new Module();
        updatedModule.setId(module.getId());
        updatedModule.setName(UPDATED_NAME);
        updatedModule.setDescription(UPDATED_DESCRIPTION);

        restModuleMockMvc.perform(put("/api/modules")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedModule)))
                .andExpect(status().isOk());

        // Validate the Module in the database
        List<Module> modules = moduleRepository.findAll();
        assertThat(modules).hasSize(databaseSizeBeforeUpdate);
        Module testModule = modules.get(modules.size() - 1);
        assertThat(testModule.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testModule.getDescription()).isEqualTo(UPDATED_DESCRIPTION);

        // Validate the Module in ElasticSearch
        Module moduleEs = moduleSearchRepository.findOne(testModule.getId());
        assertThat(moduleEs).isEqualToComparingFieldByField(testModule);
    }

    @Test
    @Transactional
    public void deleteModule() throws Exception {
        // Initialize the database
        moduleRepository.saveAndFlush(module);
        moduleSearchRepository.save(module);
        int databaseSizeBeforeDelete = moduleRepository.findAll().size();

        // Get the module
        restModuleMockMvc.perform(delete("/api/modules/{id}", module.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean moduleExistsInEs = moduleSearchRepository.exists(module.getId());
        assertThat(moduleExistsInEs).isFalse();

        // Validate the database is empty
        List<Module> modules = moduleRepository.findAll();
        assertThat(modules).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchModule() throws Exception {
        // Initialize the database
        moduleRepository.saveAndFlush(module);
        moduleSearchRepository.save(module);

        // Search the module
        restModuleMockMvc.perform(get("/api/_search/modules?query=id:" + module.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.[*].id").value(hasItem(module.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }
}
