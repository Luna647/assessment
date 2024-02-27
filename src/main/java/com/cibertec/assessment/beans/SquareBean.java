package com.cibertec.assessment.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SquareBean {
    
    private Integer id;
    private String name;
    private int npoints;
    private String polygons; // Representación de los IDs de polígonos que se superponen como un String
    private Integer[] x_points;
    private Integer[] y_points;
   
}
