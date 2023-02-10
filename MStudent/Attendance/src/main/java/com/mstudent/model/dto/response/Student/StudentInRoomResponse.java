package com.mstudent.model.dto.response.Student;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentInRoomResponse {
  private Long id;
  private String fullName;

}
