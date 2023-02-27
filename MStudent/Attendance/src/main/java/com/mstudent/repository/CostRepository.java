package com.mstudent.repository;

import com.mstudent.model.entity.Cost;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CostRepository extends JpaRepository<Cost, Long> {
  @Query("SELECT a FROM Cost a "
      + "JOIN Room r ON a.room.id = r.id "
      + "JOIN Student s ON a.student.id = s.id "
      + "WHERE r.id = :roomId AND a.student.id = :studentId "
      + "AND a.month = :month ")
  Cost findAllByRoomIdAndStudentIdAndMonth(@Param("roomId") Long roomId, @Param("studentId") Long studentId, @Param("month") String month);
}
