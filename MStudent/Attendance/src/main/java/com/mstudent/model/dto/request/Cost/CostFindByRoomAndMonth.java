package com.mstudent.model.dto.request.Cost;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CostFindByRoomAndMonth {
  private Long roomId;
  private int month;
}
