package com.mstudent.model.dto.request;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceToProcessCostRequest {
  private Long roomId;
  private Date fromDate;
  private Date toDate;
  private Long studentId;

}
