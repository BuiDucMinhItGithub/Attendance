package com.mstudent.mapper;

import com.mstudent.model.dto.response.Attendance.AttendanceResponse;
import com.mstudent.model.entity.Attendance;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-02-08T15:23:26+0700",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17.0.5 (Oracle Corporation)"
)
@Component
public class AttendanceMapperImpl implements AttendanceMapper {

    @Override
    public AttendanceResponse entityToResponse(Attendance attendance) {
        if ( attendance == null ) {
            return null;
        }

        AttendanceResponse attendanceResponse = new AttendanceResponse();

        attendanceResponse.setId( attendance.getId() );
        attendanceResponse.setDate( attendance.getDate() );
        if ( attendance.getMonth() != null ) {
            attendanceResponse.setMonth( Integer.parseInt( attendance.getMonth() ) );
        }
        attendanceResponse.setState( attendance.getState() );

        return attendanceResponse;
    }

    @Override
    public List<Attendance> listEntityToResponse(List<Attendance> attendances) {
        if ( attendances == null ) {
            return null;
        }

        List<Attendance> list = new ArrayList<Attendance>( attendances.size() );
        for ( Attendance attendance : attendances ) {
            list.add( attendance );
        }

        return list;
    }
}
