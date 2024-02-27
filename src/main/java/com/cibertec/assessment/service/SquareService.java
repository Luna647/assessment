package com.cibertec.assessment.service;

import java.util.List;

import com.cibertec.assessment.beans.SquareBean;
import com.cibertec.assessment.dto.SquareDto;
import com.cibertec.assessment.model.Square;

public interface SquareService {

	public Square create(Square s);
	
	//public List<Square> list();
	
	public List<SquareBean> list();
	
    public Square update(Square s);
	
	public void delete(int id);
	
	List<SquareDto> findSquareDtos();
}
