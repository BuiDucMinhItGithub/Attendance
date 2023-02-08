package com.mstudent.model.dto.response.Student;

import com.mstudent.model.dto.response.Cost.CostResponse;
import com.mstudent.model.dto.response.Room.RoomResponse;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentResponse {
  private Long id;
  private String fullName;
  private Date birthday;
  private List<RoomResponse> roomResponses;
  private String state;
  private List<CostResponse> costResponses;
}
