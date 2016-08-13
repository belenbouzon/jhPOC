package com.belen.pruebajh.repository;

import com.belen.pruebajh.domain.Gama;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Gama entity.
 */
@SuppressWarnings("unused")
public interface GamaRepository extends JpaRepository<Gama,Long> {

}
