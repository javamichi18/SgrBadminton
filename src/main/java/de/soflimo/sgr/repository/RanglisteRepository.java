package de.soflimo.sgr.repository;

import org.springframework.data.repository.CrudRepository;

import de.soflimo.sgr.model.Rangliste;

/**
 *
 */
public interface RanglisteRepository
    extends CrudRepository<Rangliste, Long> {

    Rangliste findByName (String name);
}
