package com.mstudent.model.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mstudent.model.entity.Student;
import com.mstudent.model.entity.Teacher;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateRoomRequest {
    private String name;
    @JsonFormat(pattern="dd-MM-yyyy")
    private Date startDate;
    private Teacher teacher;
    private int numberOfStudent;
    private List<Student> students;
}
