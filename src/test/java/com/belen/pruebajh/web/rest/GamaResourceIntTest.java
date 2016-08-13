package com.belen.pruebajh.web.rest;

import com.belen.pruebajh.PruebaApp;
import com.belen.pruebajh.domain.Gama;
import com.belen.pruebajh.repository.GamaRepository;

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
 * Test class for the GamaResource REST controller.
 *
 * @see GamaResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = PruebaApp.class)
@WebAppConfiguration
@IntegrationTest
public class GamaResourceIntTest {

    private static final String DEFAULT_CLIENTE = "AAAAA";
    private static final String UPDATED_CLIENTE = "BBBBB";
    private static final String DEFAULT_TONO = "AAAAA";
    private static final String UPDATED_TONO = "BBBBB";

    @Inject
    private GamaRepository gamaRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restGamaMockMvc;

    private Gama gama;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        GamaResource gamaResource = new GamaResource();
        ReflectionTestUtils.setField(gamaResource, "gamaRepository", gamaRepository);
        this.restGamaMockMvc = MockMvcBuilders.standaloneSetup(gamaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        gama = new Gama();
        gama.setCliente(DEFAULT_CLIENTE);
        gama.setTono(DEFAULT_TONO);
    }

    @Test
    @Transactional
    public void createGama() throws Exception {
        int databaseSizeBeforeCreate = gamaRepository.findAll().size();

        // Create the Gama

        restGamaMockMvc.perform(post("/api/gamas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(gama)))
                .andExpect(status().isCreated());

        // Validate the Gama in the database
        List<Gama> gamas = gamaRepository.findAll();
        assertThat(gamas).hasSize(databaseSizeBeforeCreate + 1);
        Gama testGama = gamas.get(gamas.size() - 1);
        assertThat(testGama.getCliente()).isEqualTo(DEFAULT_CLIENTE);
        assertThat(testGama.getTono()).isEqualTo(DEFAULT_TONO);
    }

    @Test
    @Transactional
    public void getAllGamas() throws Exception {
        // Initialize the database
        gamaRepository.saveAndFlush(gama);

        // Get all the gamas
        restGamaMockMvc.perform(get("/api/gamas?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(gama.getId().intValue())))
                .andExpect(jsonPath("$.[*].cliente").value(hasItem(DEFAULT_CLIENTE.toString())))
                .andExpect(jsonPath("$.[*].tono").value(hasItem(DEFAULT_TONO.toString())));
    }

    @Test
    @Transactional
    public void getGama() throws Exception {
        // Initialize the database
        gamaRepository.saveAndFlush(gama);

        // Get the gama
        restGamaMockMvc.perform(get("/api/gamas/{id}", gama.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(gama.getId().intValue()))
            .andExpect(jsonPath("$.cliente").value(DEFAULT_CLIENTE.toString()))
            .andExpect(jsonPath("$.tono").value(DEFAULT_TONO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingGama() throws Exception {
        // Get the gama
        restGamaMockMvc.perform(get("/api/gamas/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGama() throws Exception {
        // Initialize the database
        gamaRepository.saveAndFlush(gama);
        int databaseSizeBeforeUpdate = gamaRepository.findAll().size();

        // Update the gama
        Gama updatedGama = new Gama();
        updatedGama.setId(gama.getId());
        updatedGama.setCliente(UPDATED_CLIENTE);
        updatedGama.setTono(UPDATED_TONO);

        restGamaMockMvc.perform(put("/api/gamas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedGama)))
                .andExpect(status().isOk());

        // Validate the Gama in the database
        List<Gama> gamas = gamaRepository.findAll();
        assertThat(gamas).hasSize(databaseSizeBeforeUpdate);
        Gama testGama = gamas.get(gamas.size() - 1);
        assertThat(testGama.getCliente()).isEqualTo(UPDATED_CLIENTE);
        assertThat(testGama.getTono()).isEqualTo(UPDATED_TONO);
    }

    @Test
    @Transactional
    public void deleteGama() throws Exception {
        // Initialize the database
        gamaRepository.saveAndFlush(gama);
        int databaseSizeBeforeDelete = gamaRepository.findAll().size();

        // Get the gama
        restGamaMockMvc.perform(delete("/api/gamas/{id}", gama.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Gama> gamas = gamaRepository.findAll();
        assertThat(gamas).hasSize(databaseSizeBeforeDelete - 1);
    }
}
