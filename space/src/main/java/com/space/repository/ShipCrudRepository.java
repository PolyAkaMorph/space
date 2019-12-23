package com.space.repository;

import com.space.model.Ship;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ShipCrudRepository extends CrudRepository<Ship,Long> {

    @Query(value = "from Ship where (:name is null or lower(name) like lower(concat('%', :name,'%'))) and " +
    "(:planet is null or lower(planet) like lower(concat('%', :planet,'%')))")
    Iterable<Ship> getShipsList(@Param("name") String name, @Param("planet") String planet);

}
