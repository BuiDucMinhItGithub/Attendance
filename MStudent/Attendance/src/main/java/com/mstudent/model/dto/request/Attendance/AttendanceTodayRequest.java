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
public class AttendanceTodayRequest {
  private Long roomId;
  @JsonFormat(pattern="dd-MM-yyyy")
  private Date date;

}
