package com.cibertec.assessment.repo;

import com.cibertec.assessment.dto.SquareDto;
import com.cibertec.assessment.model.Square;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SquareRepo extends JpaRepository<Square, Integer> {

    @Query("SELECT new com.cibertec.assessment.dto.SquareDto(s.id, s.name, s.polygons) FROM Square s")
    List<SquareDto> findSquareDtos();
}
