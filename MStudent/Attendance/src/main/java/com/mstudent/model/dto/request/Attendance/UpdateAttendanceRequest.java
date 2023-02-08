package com.mstudent.model.dto.request.Attendance;

import com.mstudent.model.dto.request.Attendance.StudentAttendance;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAttendanceRequest {
    private List<StudentAttendance> studentAttendances;
    private Date date;
    private Long roomId;
}