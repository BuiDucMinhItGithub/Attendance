package com.mstudent.model.dto.response.Cost;

import com.mstudent.model.dto.response.Room.RoomResponse;
import com.mstudent.model.dto.response.Student.StudentFullResponse;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CostResponse {
  private Long id;
  private int numberOfLesson;
  private int month;
  private String state;
  private BigDecimal price;
  private StudentFullResponse studentFullResponse;
  private RoomResponse roomResponse;
}
