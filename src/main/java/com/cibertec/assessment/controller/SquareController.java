package com.cibertec.assessment.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import com.cibertec.assessment.model.Square;
import com.cibertec.assessment.beans.SquareBean;
import com.cibertec.assessment.dto.SquareDto;
import com.cibertec.assessment.service.SquareService;

@RestController
@RequestMapping("/squares")
public class SquareController {

    @Autowired
    private SquareService squareService;

    @GetMapping
    public ResponseEntity<List<SquareBean>> getAllSquares() {
    	 System.out.println("--------------ResponseEntity1---------------");
    	
        List<SquareBean> squares = squareService.list();
        return new ResponseEntity<>(squares, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Square> createSquare(@RequestBody Square square) {
    	
    	  System.out.println("--------------SquareController-CREAR SQUARE///---------------");
    	  System.out.println("X Name: " + square.getName());
    	  System.out.println("X Points: " + square.getNpoints());
    	   System.out.println("Polygons: " + square.getPolygons());
          System.out.println("X Points: " + square.getX_points());
          System.out.println("Y Points: " + square.getY_points());
       
    	
        Square createdSquare = squareService.create(square);
        
        return new ResponseEntity<>(createdSquare, HttpStatus.CREATED);
        
        
    }

    @GetMapping("/dtos")
    public ResponseEntity<List<SquareDto>> listSquareDtos() {
    	System.out.println("--------------ResponseEntity2---------------");
    	
        
        List<SquareDto> squareDtos = squareService.findSquareDtos();
        return new ResponseEntity<>(squareDtos, HttpStatus.OK);
    }

    @PutMapping
   	public ResponseEntity<Square> update(@RequestBody Square s) {
   		return new ResponseEntity<>(squareService.update(s), HttpStatus.CREATED);
   	}
   	
   	@DeleteMapping("/{id}")
   	public ResponseEntity<Void> delete(@PathVariable("id") int id) {
   		squareService.delete(id);
   		return new ResponseEntity<>(HttpStatus.OK);
   	}
}
