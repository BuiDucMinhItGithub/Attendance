package com.mstudent.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Attendance {
    private Long id;
    private String date;
    private String state;
    private String studentName;
    private String roomName;
    private String type;
}
