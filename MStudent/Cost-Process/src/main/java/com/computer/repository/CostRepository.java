package com.computer.repository;

import com.computer.model.Cost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CostRepository extends JpaRepository<Cost, Long> {

  Cost findByRoomIdAndStudentIdAndMonth(@Param("roomId") Long roomId, @Param("studentId") Long studentId, @Param("month") String month);
}
