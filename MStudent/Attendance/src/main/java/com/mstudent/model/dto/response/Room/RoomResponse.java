package com.mstudent.model.dto.response.Room;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mstudent.model.dto.response.Student.StudentFullResponse;
import com.mstudent.model.dto.response.Teacher.TeacherResponse;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoomResponse {
  private Long id;
  @JsonFormat(pattern="dd-MM-yyyy")
  private Date startDate;
  @JsonFormat(pattern="dd-MM-yyyy")
  private Date endDate;
  private TeacherResponse teacherResponse;
  private int numberOfStudent;
  private String state;
  private BigDecimal pricePerLesson;
  private List<StudentFullResponse> studentFullRespons;
}
