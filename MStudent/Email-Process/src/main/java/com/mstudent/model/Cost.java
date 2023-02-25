package com.mstudent.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Cost {
  private Long id;
  private Long attendanceId;
  private int month;
  private String date;
  private String state;
  private String studentName;
  private String roomName;
  private String type;
}
