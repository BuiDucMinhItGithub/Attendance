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
public class AttendanceRangeDateRequest {
  private Long roomId;
  @JsonFormat(pattern="dd-MM-yyyy")
  private Date fromDate;
  @JsonFormat(pattern="dd-MM-yyyy")
  private Date toDate;

}
