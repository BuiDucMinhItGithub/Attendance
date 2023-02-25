package com.mstudent.model.dto.response.Cost;

import com.mstudent.model.dto.response.Room.RoomCostResponse;
import com.mstudent.model.dto.response.Room.RoomResponse;
import com.mstudent.model.dto.response.Student.StudentFullResponse;
import java.math.BigDecimal;

import com.mstudent.model.dto.response.Student.StudentResponse;
import com.mstudent.model.dto.response.Student.StudentShortResponse;
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
  private StudentShortResponse studentShortResponse;
  private RoomCostResponse roomCostResponse;
}
