package com.mstudent.model.dto.request.Teacher;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CreateTeacherRequest {
    private String fullName;
    private String userName;
    private String password;
    private String phone;
    private String address;
    @JsonFormat(pattern="dd-MM-yyyy")
    private Date birthday;
}
