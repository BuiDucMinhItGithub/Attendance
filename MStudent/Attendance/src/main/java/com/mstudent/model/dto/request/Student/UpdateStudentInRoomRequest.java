package com.mstudent.model.dto.request.Student;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateStudentInRoomRequest {
  private Long id;
  private String fullName;
}
