package com.cibertec.assessment.dto;

public class SquareDto {

    private final Integer id;
    private final String name;
    private final String polygons; 

    public SquareDto(Integer id, String name, String polygons) {
        this.id = id;
        this.name = name;
        this.polygons = polygons;
    }

    // Getters
    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPolygons() {
        return polygons;
    }
}
