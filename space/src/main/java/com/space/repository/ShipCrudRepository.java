package com.space.repository;

import com.space.model.Ship;
import com.space.model.ShipType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface ShipCrudRepository extends CrudRepository<Ship,Long> {
    @Query(value = "from Ship where (:name is null or lower(name) like lower(concat('%', :name,'%'))) and " +
            "(:planet is null or lower(planet) like lower(concat('%', :planet,'%')))")
    Iterable<Ship> getShipsStable(@Param("name") String name,
                                  @Param("planet") String planet);

    @Query(value = "from Ship where (:name is null or lower(name) like lower(concat('%', :name,'%'))) and " +
    "(:planet is null or lower(planet) like lower(concat('%', :planet,'%'))) and " +
    "(:shipType is null or shipType = :shipType) and (prodDate between :after and :before) and" +
    "(isUsed = :isUsed) and (speed between :minSpeed and :maxSpeed) and " +
            "(crewSize between :minCrewSize and :maxCrewSize) and " +
            "(rating between :minRating and :maxRating)")
    Iterable<Ship> getShipsList(@Param("name") String name,
                                @Param("planet") String planet,
                                @Param("shipType") ShipType shipType,
                                @Param("after") Date after,
                                @Param("before") Date before,
                                @Param("isUsed") Boolean isUsed,
                                @Param("minSpeed") Double minSpeed,
                                @Param("maxSpeed") Double maxSpeed,
                                @Param("minCrewSize") Integer minCrewSize,
                                @Param("maxCrewSize") Integer maxCrewSize,
                                @Param("minRating") Double minRating,
                                @Param("maxRating") Double maxRating);
}

