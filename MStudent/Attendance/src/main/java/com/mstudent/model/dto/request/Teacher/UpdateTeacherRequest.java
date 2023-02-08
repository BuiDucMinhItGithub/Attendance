package com.mstudent.model.dto.request.Teacher;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mstudent.model.entity.Room;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class UpdateTeacherRequest {
    private Long id;
    private String fullName;
    private String userName;
    private String password;
    private String address;
    private String phone;
    @JsonFormat(pattern="dd-MM-yyyy")
    private Date birthday;
    private String state;
}
