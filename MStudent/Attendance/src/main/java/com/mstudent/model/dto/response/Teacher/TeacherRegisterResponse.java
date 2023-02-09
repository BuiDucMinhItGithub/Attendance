package com.mstudent.model.dto.response.Teacher;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class TeacherRegisterResponse {
  private Long id;
  private String fullName;
  private String userName;
  @JsonFormat(pattern="dd-MM-yyyy")
  private Date birthday;
  private String password;
  private String phone;
  private String address;
  private String state;
  private String role;
}
