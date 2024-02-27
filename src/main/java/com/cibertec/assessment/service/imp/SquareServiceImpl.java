package com.cibertec.assessment.service.imp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cibertec.assessment.beans.PolygonBean;
import com.cibertec.assessment.beans.SquareBean;
import com.cibertec.assessment.dto.SquareDto;
import com.cibertec.assessment.model.Polygon;
import com.cibertec.assessment.model.Square;
import com.cibertec.assessment.repo.SquareRepo;
import com.cibertec.assessment.service.PolygonService;
import com.cibertec.assessment.service.SquareService;

@Service
public class SquareServiceImpl implements SquareService {

    private final SquareRepo squareRepo;
    private final PolygonService polygonService;

    @Autowired
    public SquareServiceImpl(SquareRepo squareRepo, PolygonService polygonService) {
        this.squareRepo = squareRepo;
        this.polygonService = polygonService;
    }

    @Override
    public Square create(Square s) {
    	
    	
    	System.out.println("--------------CREAR SQUARE 2---------------");
        System.out.println("X Points: " + s.getX_points());
        System.out.println("Y Points: " + s.getY_points());
        System.out.println("Number of Points: " + s.getNpoints());
    	
        s.setX_points(s.getX_points() == null || s.getX_points().isBlank() ? "[]" : s.getX_points());
        s.setY_points(s.getY_points() == null || s.getY_points().isBlank() ? "[]" : s.getY_points());

        List<String> overlappingPolygonIds = new ArrayList<>();

        
        
        Integer[] xPointsSquare = convertStringToIntArray(s.getX_points());
        System.out.println("X Points prueba: " + xPointsSquare.length);
        Integer[] yPointsSquare = convertStringToIntArray(s.getY_points());
        System.out.println("X Points prueba: " + xPointsSquare.length);

        List<Polygon> allPolygons = polygonService.list().stream()
                .map(this::convertPolygonBeanToPolygon)
                .collect(Collectors.toList());

        for (Polygon p : allPolygons) {
            if (checkOverlap(p, xPointsSquare, yPointsSquare)) {
                overlappingPolygonIds.add(p.getId().toString());
            }
        }

        String polygonsArrayString = overlappingPolygonIds.toString();
        System.out.println("Ver polygonsArrayString: " + polygonsArrayString.length());
        s.setPolygons(polygonsArrayString);

        return squareRepo.save(s);
    }

    @Override
    public List<SquareBean> list() {
        List<Square> list = squareRepo.findAll();
        List<SquareBean> listSquareBeans = new ArrayList<>();
        list.forEach(square -> {
            Integer[] intArrayX = convertStringToIntArray(square.getX_points());
            Integer[] intArrayY = convertStringToIntArray(square.getY_points());

            
            intArrayX = Arrays.copyOf(intArrayX, 4);
            intArrayY = Arrays.copyOf(intArrayY, 4);

            SquareBean squareBean = new SquareBean();
            squareBean.setId(square.getId());
            squareBean.setName(square.getName());
            squareBean.setNpoints(square.getNpoints());
            squareBean.setPolygons(square.getPolygons());
            squareBean.setX_points(intArrayX);
            squareBean.setY_points(intArrayY);

            listSquareBeans.add(squareBean);
        });
        return listSquareBeans;
    }

    private Polygon convertPolygonBeanToPolygon(PolygonBean polygonBean) {
        return new Polygon(polygonBean.getId(), polygonBean.getName(), polygonBean.getNpoints(),
                Arrays.toString(polygonBean.getX_points()), Arrays.toString(polygonBean.getY_points()));
    }

    private Integer[] convertStringToIntArray(String arrayAsString) {
        try {
            System.out.println("Original String: " + arrayAsString); // Depuración
            arrayAsString = arrayAsString.replaceAll("\\[", "").replaceAll("\\]", "").trim();
            if (arrayAsString.isEmpty()) {
                return new Integer[0];
            }
            String[] items = arrayAsString.split(",");
            return Arrays.stream(items)
                    .map(String::trim)
                    .filter(item -> !item.isEmpty())
                    .map(Integer::valueOf)
                    .toArray(Integer[]::new);
        } catch (NumberFormatException e) {
            // Manejar la excepción, por ejemplo, registrándola y devolviendo null o un array vacío
            e.printStackTrace();
            return new Integer[0];
        }
    }

    private boolean checkOverlap(Polygon p, Integer[] xPointsSquare, Integer[] yPointsSquare) {
        Integer[] xPointsPolygon = convertStringToIntArray(p.getX_points());
        Integer[] yPointsPolygon = convertStringToIntArray(p.getY_points());

        for (int i = 0; i < xPointsSquare.length; i++) {
            if (isPointInsidePolygon(p, xPointsSquare[i], yPointsSquare[i])) {
                return true;
            }
        }
        return false;
    }

    private boolean isPointInsidePolygon(Polygon polygon, int x, int y) {
        Integer[] xPoints = convertStringToIntArray(polygon.getX_points());
        Integer[] yPoints = convertStringToIntArray(polygon.getY_points());

        boolean inside = false;
        for (int i = 0, j = xPoints.length - 1; i < xPoints.length; j = i++) {
            if ((yPoints[i] > y) != (yPoints[j] > y) &&
                (x < (xPoints[j] - xPoints[i]) * (y - yPoints[i]) / (yPoints[j] - yPoints[i]) + xPoints[i])) {
                inside = !inside;
            }
        }
        return inside;
    }

    @Override
    public List<SquareDto> findSquareDtos() {
        return squareRepo.findSquareDtos();
    }
	
	@Override
	public void delete(int id) {
		squareRepo.deleteById(id);
		
	}

	@Override
	public Square update(Square s) {
		
		return squareRepo.save(s);
	}
}