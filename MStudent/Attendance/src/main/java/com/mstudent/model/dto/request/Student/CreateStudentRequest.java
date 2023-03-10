package com.mstudent.model.dto.request.Student;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateStudentRequest {
    private String fullName;
    @JsonFormat(pattern="dd-MM-yyyy")
    private Date birthday;
}
