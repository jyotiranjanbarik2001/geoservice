package com.example.demo.service;


import com.example.demo.dto.PlaceRequest;
import com.example.demo.Entity.Place;
import com.example.demo.repository.PlaceRepository;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PlaceService {

    private final PlaceRepository repository;
    private final GeometryFactory geometryFactory = new GeometryFactory();

    public PlaceService(PlaceRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Place savePlace(PlaceRequest req) {
        Point point = geometryFactory.createPoint(new Coordinate(req.getLongitude(), req.getLatitude()));
        point.setSRID(4326);
        Place place = new Place();
        place.setName(req.getName());
        place.setType(req.getType());
        place.setLatitude(req.getLatitude());
        place.setLongitude(req.getLongitude());
        place.setLocation(point);
        return repository.save(place);
    }

    @Transactional(readOnly = true)
    public List<Place> findNearby(double lat, double lon, double radiusKm) {
        double radiusMeters = radiusKm * 1000.0;
        return repository.findNearby(lat, lon, radiusMeters);
    }

    @Transactional(readOnly = true)
    public Place findNearest(double lat, double lon) {
        return repository.findNearest(lat, lon);
    }

    @Transactional(readOnly = true)
    public Double distanceBetween(Long id1, Long id2) {
        return repository.distanceBetween(id1, id2);
    }

    @Transactional(readOnly = true)
    public Optional<Place> findById(Long id) {
        return repository.findById(id);
    }
}
