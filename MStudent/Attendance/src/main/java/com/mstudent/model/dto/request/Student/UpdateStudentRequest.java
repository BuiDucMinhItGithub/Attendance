package com.mstudent.model.dto.request.Student;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateStudentRequest {
    private Long id;
    private String fullName;
    @JsonFormat(pattern="dd-MM-yyyy")
    private Date birthday;
    private String state;
}
