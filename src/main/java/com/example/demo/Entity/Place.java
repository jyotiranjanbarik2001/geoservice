package com.example.demo.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import org.locationtech.jts.geom.Point;

@Entity
@Table(name = "places")
public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String type;

    private Double latitude;
    private Double longitude;

    @JsonIgnore // prevent the "envelope" JSON recursion
    @Column(columnDefinition = "geometry(Point, 4326)")
    private Point location;

    public Place() {}

    public Place(String name, String type, Double latitude, Double longitude, Point location) {
        this.name = name;
        this.type = type;
        this.latitude = latitude;
        this.longitude = longitude;
        this.location = location;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }

    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }

    public Point getLocation() { return location; }
    public void setLocation(Point location) { this.location = location; }


    @JsonProperty("latitude")
    public Double jsonLatitude() {
        return latitude != null ? latitude : (location != null ? location.getY() : null);
    }

    @JsonProperty("longitude")
    public Double jsonLongitude() {
        return longitude != null ? longitude : (location != null ? location.getX() : null);
    }
}
