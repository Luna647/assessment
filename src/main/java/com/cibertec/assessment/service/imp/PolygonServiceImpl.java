package com.cibertec.assessment.service.imp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cibertec.assessment.beans.PolygonBean;
import com.cibertec.assessment.model.Polygon;
import com.cibertec.assessment.repo.PolygonRepo;
import com.cibertec.assessment.service.PolygonService;

@Service
public class PolygonServiceImpl implements PolygonService {

	@Autowired
	PolygonRepo polygonRepo;

	@Override
	public void create(Polygon p) {
		polygonRepo.save(p);
	}

	@Override
	public void create(List<Polygon> lp) {
		polygonRepo.saveAll(lp);
	}

	@Override
	public List<PolygonBean> list() {
		List<Polygon> list = polygonRepo.findAll();
		List<PolygonBean> listPolygonBeans = new ArrayList<>();
		list.forEach(p -> {
			Integer[] intArrayX = new Integer[5];
			Integer[] intArrayY = new Integer[5];

			convertStringInIntegerArray(p.getX_points(), p.getY_points(), intArrayX, intArrayY);

			PolygonBean polygonBean = new PolygonBean();
			polygonBean.setId(p.getId());
			polygonBean.setName(p.getName());
			polygonBean.setX_points(intArrayX);
			polygonBean.setY_points(intArrayY);
			polygonBean.setNpoints(p.getNpoints());

			listPolygonBeans.add(polygonBean);
			
			 // Imprimir la información del polígono en la consola
	       /* System.out.println("Polygon ID: " + p.getId());
	        System.out.println("Polygon Name: " + p.getName());
	        System.out.println("X Points: " + Arrays.toString(intArrayX));
	        System.out.println("Y Points: " + Arrays.toString(intArrayY));
	        System.out.println("Number of Points: " + p.getNpoints());
	        System.out.println("-----------------------------------------");*/
		});

		return listPolygonBeans;
	}

	private void convertStringInIntegerArray(String xPoints, String yPoints, Integer[] intArrayX, Integer[] intArrayY) {
	    if (xPoints == null || yPoints == null) {
	        // Logica para manejar strings nulos. Por ejemplo, puedes decidir dejar los arrays como vacíos.
	       
	        Arrays.fill(intArrayX, 0); 
	        Arrays.fill(intArrayY, 0);
	        return; 
	    }

	    String cleanedXPoints = xPoints.substring(1, xPoints.length() - 1);
	    String cleanedYPoints = yPoints.substring(1, yPoints.length() - 1);

	    // El proceso de división y conversión continúa aquí...
	    String[] partsX = cleanedXPoints.split(", ");
	    String[] partsY = cleanedYPoints.split(", ");

	    for (int i = 0; i < partsX.length; i++) {
	        intArrayX[i] = Integer.parseInt(partsX[i]);
	    }

	    for (int i = 0; i < partsY.length; i++) {
	        intArrayY[i] = Integer.parseInt(partsY[i]);
	    }
	}

	@Override
	public Polygon update(Polygon p) {
		
		return polygonRepo.save(p);
	}

	@Override
	public void delete(int id) {
		polygonRepo.deleteById(id);
		
	}

}
