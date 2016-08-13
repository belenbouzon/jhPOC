package com.belen.pruebajh.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.belen.pruebajh.domain.Gama;
import com.belen.pruebajh.repository.GamaRepository;
import com.belen.pruebajh.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Gama.
 */
@RestController
@RequestMapping("/api")
public class GamaResource {

    private final Logger log = LoggerFactory.getLogger(GamaResource.class);
        
    @Inject
    private GamaRepository gamaRepository;
    
    /**
     * POST  /gamas : Create a new gama.
     *
     * @param gama the gama to create
     * @return the ResponseEntity with status 201 (Created) and with body the new gama, or with status 400 (Bad Request) if the gama has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/gamas",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Gama> createGama(@RequestBody Gama gama) throws URISyntaxException {
        log.debug("REST request to save Gama : {}", gama);
        if (gama.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("gama", "idexists", "A new gama cannot already have an ID")).body(null);
        }
        Gama result = gamaRepository.save(gama);
        return ResponseEntity.created(new URI("/api/gamas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("gama", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /gamas : Updates an existing gama.
     *
     * @param gama the gama to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated gama,
     * or with status 400 (Bad Request) if the gama is not valid,
     * or with status 500 (Internal Server Error) if the gama couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/gamas",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Gama> updateGama(@RequestBody Gama gama) throws URISyntaxException {
        log.debug("REST request to update Gama : {}", gama);
        if (gama.getId() == null) {
            return createGama(gama);
        }
        Gama result = gamaRepository.save(gama);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("gama", gama.getId().toString()))
            .body(result);
    }

    /**
     * GET  /gamas : get all the gamas.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of gamas in body
     */
    @RequestMapping(value = "/gamas",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Gama> getAllGamas() {
        log.debug("REST request to get all Gamas");
        List<Gama> gamas = gamaRepository.findAll();
        return gamas;
    }

    /**
     * GET  /gamas/:id : get the "id" gama.
     *
     * @param id the id of the gama to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the gama, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/gamas/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Gama> getGama(@PathVariable Long id) {
        log.debug("REST request to get Gama : {}", id);
        Gama gama = gamaRepository.findOne(id);
        return Optional.ofNullable(gama)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /gamas/:id : delete the "id" gama.
     *
     * @param id the id of the gama to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/gamas/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteGama(@PathVariable Long id) {
        log.debug("REST request to delete Gama : {}", id);
        gamaRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("gama", id.toString())).build();
    }

}
