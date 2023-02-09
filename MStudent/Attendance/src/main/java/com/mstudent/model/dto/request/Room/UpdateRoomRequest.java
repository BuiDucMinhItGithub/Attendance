package com.mstudent.model.dto.request.Room;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mstudent.model.dto.request.Student.UpdateStudentInRoomRequest;
import com.mstudent.model.entity.Student;
import com.mstudent.model.entity.Teacher;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class UpdateRoomRequest {
    private Long id;
    private String name;
    @JsonFormat(pattern="dd-MM-yyyy")
    private Date startDate;
    private Teacher teacher;
    private int numberOfStudent;
    private BigDecimal pricePerLesson;
    private String state;
    private List<UpdateStudentInRoomRequest> students;
}
