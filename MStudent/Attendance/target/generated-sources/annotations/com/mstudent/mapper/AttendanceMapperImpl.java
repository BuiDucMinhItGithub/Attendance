package com.mstudent.mapper;

import com.mstudent.model.dto.response.Attendance.AttendanceResponse;
import com.mstudent.model.entity.Attendance;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-03-05T14:51:58+0700",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17.0.5 (Oracle Corporation)"
)
@Component
public class AttendanceMapperImpl implements AttendanceMapper {

    @Autowired
    private RoomMapper roomMapper;

    @Override
    public AttendanceResponse entityToResponse(Attendance attendance) {

        AttendanceResponse attendanceResponse = new AttendanceResponse();

        if ( attendance != null ) {
            if ( attendance.getRoom() != null ) {
                attendanceResponse.setRoomAttendanceResponse( roomMapper.entityToRoomAttendance( attendance.getRoom() ) );
            }
            if ( attendance.getId() != null ) {
                attendanceResponse.setId( attendance.getId() );
            }
            if ( attendance.getDate() != null ) {
                attendanceResponse.setDate( attendance.getDate() );
            }
            if ( attendance.getMonth() != null ) {
                attendanceResponse.setMonth( attendance.getMonth() );
            }
            if ( attendance.getState() != null ) {
                attendanceResponse.setState( attendance.getState() );
            }
        }

        return attendanceResponse;
    }

    @Override
    public List<AttendanceResponse> listEntityToResponse(List<Attendance> attendances) {
        if ( attendances == null ) {
            return new ArrayList<AttendanceResponse>();
        }

        List<AttendanceResponse> list = new ArrayList<AttendanceResponse>( attendances.size() );
        for ( Attendance attendance : attendances ) {
            list.add( entityToResponse( attendance ) );
        }

        return list;
    }
}
