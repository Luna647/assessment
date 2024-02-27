package com.cibertec.assessment.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cibertec.assessment.beans.PolygonBean;
import com.cibertec.assessment.model.Polygon;
import com.cibertec.assessment.service.PolygonService;



@RestController
@RequestMapping("/polygons")
public class PolygonController {

    @Autowired
    private PolygonService polygonService;

    @GetMapping
    public ResponseEntity<List<PolygonBean>> getAllPolygons() {
        List<PolygonBean> polygons = polygonService.list();
        return new ResponseEntity<>(polygons, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Polygon> createPolygon(@RequestBody Polygon polygon) {
        polygonService.create(polygon);
        return new ResponseEntity<>(polygon, HttpStatus.CREATED);
    }

    @PostMapping("/batch")
    public ResponseEntity<List<Polygon>> createPolygons(@RequestBody List<Polygon> polygons) {
        polygonService.create(polygons);
        return new ResponseEntity<>(polygons, HttpStatus.CREATED);
    }

    @PutMapping
	public ResponseEntity<Polygon> update(@RequestBody Polygon p) {
		return new ResponseEntity<>(polygonService.update(p), HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") int id) {
		polygonService.delete(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
