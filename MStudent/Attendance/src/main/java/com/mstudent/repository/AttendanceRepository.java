package com.mstudent.repository;

import com.mstudent.model.entity.Attendance;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
  @Query("SELECT a FROM Attendance a JOIN Room b ON a.room.id = b.id WHERE a.room.id = :roomId AND a.date < :date ")
  List<Attendance> findAllByRoomIdAndDate(@Param("roomId") Long roomId, @Param("date") LocalDate date);
  @Query("SELECT a FROM Attendance a "
      + "JOIN Room b ON a.room.id = b.id "
      + "JOIN Student s ON a.student.id = s.id "
      + "WHERE a.room.id = :roomId "
      + "AND s.id = :studentId "
      + "AND a.date = :date AND a.state = :state")
  Attendance findByRoomIdAndStudentIdWithDateAndState(@Param("roomId") Long roomId, @Param("studentId") Long studentId, @Param("date") LocalDate date, @Param("state") String state);

  @Query("SELECT a FROM Attendance a "
      + "JOIN Room b ON a.room.id = b.id "
      + "JOIN Student s ON a.student.id = s.id "
      + "WHERE a.room.id = :roomId "
      + "AND s.id = :studentId "
      + "AND a.date = :date")
  Attendance findByRoomIdAndStudentIdWithDate(@Param("roomId") Long roomId, @Param("studentId") Long studentId, @Param("date") LocalDate date);
  @Query("SELECT a FROM Attendance a "
      + "JOIN Room r ON a.room.id = r.id "
      + "WHERE r.id = :roomId "
      + "AND a.date > :fromDate "
      + "AND a.date < :toDate")
  List<Attendance> findByRoomIdAndRangeDate(@Param("fromDate") LocalDate fromDate, @Param("toDate") LocalDate toDate, @Param("roomId") Long roomId);

  @Query("SELECT a FROM Attendance a "
      + "JOIN Room r ON a.room.id = r.id "
      + "JOIN Student s ON a.student.id = s.id "
      + "WHERE r.id = :roomId "
      + "AND s.id = :studentId "
      + "AND a.date > :fromDate "
      + "AND a.date < :toDate")
  List<Attendance> findAllByRoomIdAndStudentIdWithRangeDate(@Param("fromDate") LocalDate fromDate, @Param("toDate") LocalDate toDate, @Param("roomId")
  Long roomId, @Param("studentId") Long studentId);

  @Query("SELECT a FROM Attendance a "
      + "JOIN Room r ON a.room.id = r.id "
      + "JOIN Student s ON a.student.id = s.id "
      + "WHERE r.id = :roomId "
      + "AND s.id = :studentId "
      + "AND a.month = :month AND a.state = :state ")
  List<Attendance> findAllByRoomIdAndStudentIdAndMonthAndState(@Param("month") String month, @Param("roomId") Long roomId, @Param("studentId")
  Long studentId, @Param("state") String state);

}
