package com.space.repository;

import com.space.model.Ship;
import com.space.model.ShipType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface ShipCrudRepository extends CrudRepository<Ship,Long> {
    @Query(value = "from Ship where (:name is null or lower(name) like lower(concat('%', :name,'%'))) and " +
            "(:planet is null or lower(planet) like lower(concat('%', :planet,'%')))")
    Iterable<Ship> getShipsStable(@Param("name") String name,
                                  @Param("planet") String planet);

    @Query(value = "select s from Ship s " +
            "where (:name is null or lower(s.name) like lower(concat('%', :name,'%'))) and " +
    "(:planet is null or lower(s.planet) like lower(concat('%', :planet,'%'))) and " +
    "(:shipType is null or s.shipType = :shipType) and (s.prodDate between :after and :before) and" +
    "(:isUsed is null or s.isUsed = :isUsed) and (s.speed between :minSpeed and :maxSpeed) and " +
            "(s.crewSize between :minCrewSize and :maxCrewSize) and " +
            "(s.rating between :minRating and :maxRating)")
    Page<Ship> getShipsList(@Param("name") String name,
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
                            @Param("maxRating") Double maxRating,
                             Pageable pageable);
}

