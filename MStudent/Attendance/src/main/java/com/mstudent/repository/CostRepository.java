package com.mstudent.repository;

import com.mstudent.model.entity.Cost;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CostRepository extends JpaRepository<Cost, Long> {
  @Query("SELECT a FROM Cost a JOIN Room r ON a.room.id = r.id WHERE r.id = :roomId AND a.month = :month")
  List<Cost> findAllByRoomAndMonth(Long roomId, int month);
}
