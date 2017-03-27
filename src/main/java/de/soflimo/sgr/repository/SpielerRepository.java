package de.soflimo.sgr.repository;

import de.soflimo.sgr.model.Spieler;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

/**
 *
 */
public interface SpielerRepository
    extends CrudRepository<Spieler, Long> {

    List<Spieler> findByNachname (String name);

    Spieler findByEmail(String email);
}
