package com.mstudent.repository;

import com.mstudent.model.entity.Attendance;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
  @Query("SELECT a FROM Attendance a JOIN Room b ON a.room.id = b.id WHERE a.room.id = :roomId AND a.date = :date ")
  List<Attendance> findAllByRoomIdAndDate(Long roomId, Date date);
  @Query("SELECT a FROM Attendance a "
      + "JOIN Room b ON a.room.id = b.id "
      + "JOIN Student s ON a.student.id = s.id "
      + "WHERE a.room.id = :roomId "
      + "AND s.id = :studentId "
      + "AND a.date = :date AND a.state = :state")
  Attendance findByRoomAndStudentIdWithDateAndState(Long roomId, Long studentId, Date date, String state);

  @Query("SELECT a FROM Attendance a "
      + "JOIN Room b ON a.room.id = b.id "
      + "JOIN Student s ON a.student.id = s.id "
      + "WHERE a.room.id = :roomId "
      + "AND s.id = :studentId "
      + "AND a.date = :date")
  Attendance findByRoomAndStudentIdWithDate(Long roomId, Long studentId, Date date);
  @Query("SELECT a FROM Attendance a "
      + "JOIN Room r ON a.room.id = r.id "
      + "WHERE r.id = :roomId "
      + "AND a.date > :fromDate "
      + "AND a.date < :toDate")
  List<Attendance> findByRoomAndDate(Date fromDate, Date toDate, Long roomId);

  @Query("SELECT a FROM Attendance a "
      + "JOIN Room r ON a.room.id = r.id "
      + "JOIN Student s ON a.student.id = s.id "
      + "WHERE r.id = :roomId "
      + "AND s.id = :studentId "
      + "AND a.date > :fromDate "
      + "AND a.date < :toDate")
  List<Attendance> findAllByRoomAndStudentIdWithFromToDate(Date fromDate, Date toDate, Long roomId, Long studentId);

  @Query("SELECT a FROM Attendance a "
      + "JOIN Room r ON a.room.id = r.id "
      + "JOIN Student s ON a.student.id = s.id "
      + "WHERE r.id = :roomId "
      + "AND s.id = :studentId "
      + "AND a.month = :month")
  List<Attendance> findAllByRoomAndStudentIdAndMonth(String month, Long roomId, Long studentId);

}
