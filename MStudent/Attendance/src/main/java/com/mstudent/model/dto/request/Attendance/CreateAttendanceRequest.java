package com.mstudent.model.dto.request.Attendance;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class CreateAttendanceRequest {
    private List<StudentAttendance> studentAttendances;
    @JsonFormat(pattern="dd-MM-yyyy")
    private Date date;
    private Long roomId;
}
