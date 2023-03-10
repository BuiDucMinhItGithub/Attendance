package com.mstudent.model.dto.response.Student;

import java.util.Date;
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
  private String state;
}
