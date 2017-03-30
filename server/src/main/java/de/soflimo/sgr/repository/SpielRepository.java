package de.soflimo.sgr.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import de.soflimo.sgr.model.Spiel;

/**
 *
 */
public interface SpielRepository
    extends CrudRepository<Spiel, Long> {

    List<Spiel> findByGespieltFalseOrderByGefordertAm ();

    List<Spiel> findByGespieltTrueOrderByGespieltAmDesc ();
}
