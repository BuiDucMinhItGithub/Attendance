package com.mstudent.mapper;

import com.mstudent.model.dto.request.CreateAttendanceRequest;
import com.mstudent.model.entity.Attendance;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-02-05T00:21:18+0700",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17.0.5 (Oracle Corporation)"
)
@Component
public class AttendanceMapperImpl implements AttendanceMapper {

    @Override
    public Attendance createRequestToEntity(CreateAttendanceRequest createAttendanceRequest) {
        if ( createAttendanceRequest == null ) {
            return null;
        }

        Attendance attendance = new Attendance();

        attendance.setDate( createAttendanceRequest.getDate() );

        return attendance;
    }
}
