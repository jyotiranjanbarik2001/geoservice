package com.example.demo.Controller;

import com.example.demo.Entity.Place;
import com.example.demo.dto.PlaceRequest;
import com.example.demo.service.PlaceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/places")
public class PlaceController {

    private final PlaceService service;

    public PlaceController(PlaceService service) {
        this.service = service;
    }

    // Add new place
    @PostMapping
    public ResponseEntity<Place> addPlace(@RequestBody PlaceRequest request) {
        Place saved = service.savePlace(request);
        return ResponseEntity.ok(saved);
    }

    // Find nearby
    @GetMapping("/nearby")
    public ResponseEntity<List<Place>> findNearby(@RequestParam double lat,
                                                  @RequestParam double lon,
                                                  @RequestParam double radius /* kilometers */) {
        List<Place> places = service.findNearby(lat, lon, radius);
        return ResponseEntity.ok(places);
    }

    // Find nearest
    @GetMapping("/nearest")
    public ResponseEntity<Place> findNearest(@RequestParam double lat, @RequestParam double lon) {
        Place p = service.findNearest(lat, lon);
        return ResponseEntity.ok(p);
    }

    // Distance between two places
    @GetMapping("/distance")
    public ResponseEntity<?> distanceBetween(@RequestParam Long id1, @RequestParam Long id2) {
        Double meters = service.distanceBetween(id1, id2);
        if (meters == null) {
            return ResponseEntity.badRequest().body("One or both place ids not found.");
        }
        double kilometers = meters / 1000.0;
        return ResponseEntity.ok(kilometers);
    }
}
