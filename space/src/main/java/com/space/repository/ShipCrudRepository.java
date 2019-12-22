package com.space.repository;

import com.space.model.Ship;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShipCrudRepository extends CrudRepository<Ship,Long> {
    Iterable<Ship> findByNameIgnoreCaseContainingAndPlanetIgnoreCaseContaining(String name, String planet);

    @Query("from Ship s where(:name is null or s.name = :name)")
    // and (:planet is null or s.planet = :planet)
    Iterable<Ship> findShips(String name);

}
