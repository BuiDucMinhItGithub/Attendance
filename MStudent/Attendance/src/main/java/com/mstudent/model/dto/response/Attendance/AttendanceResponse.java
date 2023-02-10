package com.mstudent.model.dto.response.Attendance;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mstudent.model.dto.response.Room.RoomResponse;
import com.mstudent.model.dto.response.Student.StudentFullResponse;
import java.util.Date;
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
  private Date date;
  private int month;
  private String state;
  private StudentFullResponse studentFullResponse;
  private RoomResponse roomResponse;
}
