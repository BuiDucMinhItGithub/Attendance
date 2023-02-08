package com.mstudent.model.dto.request.Attendance;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceRangeDateRequest {
  private Long roomId;
  private Date fromDate;
  private Date toDate;

}