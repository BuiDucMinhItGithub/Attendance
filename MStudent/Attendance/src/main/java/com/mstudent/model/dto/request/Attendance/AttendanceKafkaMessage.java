package com.mstudent.model.dto.request.Attendance;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceKafkaMessage {
  private Long id;
  private String date;
  private String state;
  private String studentName;
  private String roomName;
  private String type;
}
