package com.example.demo.repository;

import com.example.demo.Entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {


    @Query(value = "SELECT * FROM places " +
            "WHERE ST_DWithin(location::geography, ST_SetSRID(ST_MakePoint(:lon, :lat), 4326)::geography, :radiusMeters)",
            nativeQuery = true)
    List<Place> findNearby(double lat, double lon, double radiusMeters);


    @Query(value = "SELECT * FROM places " +
            "ORDER BY location <-> ST_SetSRID(ST_MakePoint(:lon, :lat), 4326) " +
            "LIMIT 1",
            nativeQuery = true)
    Place findNearest(double lat, double lon);


    @Query(value = "SELECT ST_Distance(a.location::geography, b.location::geography) " +
            "FROM places a, places b " +
            "WHERE a.id = :id1 AND b.id = :id2",
            nativeQuery = true)
    Double distanceBetween(Long id1, Long id2);
}
