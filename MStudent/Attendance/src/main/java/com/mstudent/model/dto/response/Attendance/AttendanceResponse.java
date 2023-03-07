package com.mstudent.model.dto.response.Attendance;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mstudent.model.dto.response.Room.RoomAttendanceResponse;
import com.mstudent.model.dto.response.Room.RoomResponse;
import com.mstudent.model.dto.response.Student.StudentFullResponse;

import java.time.LocalDate;
import java.util.Date;

import com.mstudent.model.dto.response.Student.StudentResponse;
import com.mstudent.model.dto.response.Student.StudentShortResponse;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceResponse {
  private Long id;
  @JsonFormat(pattern="dd-MM-yyyy")
  @Temporal(TemporalType.DATE)
  private LocalDate date;
  private String month;
  private String state;
  private StudentShortResponse studentShortResponse;
  private RoomAttendanceResponse roomAttendanceResponse;
}
