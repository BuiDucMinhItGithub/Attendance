package com.mstudent.model.dto.response.Room;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mstudent.model.dto.response.Teacher.TeacherResponse;
import java.math.BigDecimal;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoomCreateResponse {
  private Long id;

  private String name;
  @JsonFormat(pattern="dd-MM-yyyy")
  private Date startDate;
  private TeacherResponse teacherResponse;
  private int numberOfStudent;
  private String state;
  private BigDecimal pricePerLesson;
}
