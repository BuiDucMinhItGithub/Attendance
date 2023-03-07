package com.mstudent.mapper;

import com.mstudent.model.dto.request.Room.CreateRoomRequest;
import com.mstudent.model.dto.request.Room.UpdateRoomRequest;
import com.mstudent.model.dto.response.Room.RoomAttendanceResponse;
import com.mstudent.model.dto.response.Room.RoomCostResponse;
import com.mstudent.model.dto.response.Room.RoomCreateResponse;
import com.mstudent.model.dto.response.Room.RoomResponse;
import com.mstudent.model.dto.response.Room.RoomShortResponse;
import com.mstudent.model.dto.response.Room.RoomUpdateResponse;
import com.mstudent.model.dto.response.Student.StudentShortResponse;
import com.mstudent.model.entity.Room;
import com.mstudent.model.entity.Student;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-03-06T15:04:10+0700",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17.0.5 (Oracle Corporation)"
)
@Component
public class RoomMapperImpl implements RoomMapper {

    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private TeacherMapper teacherMapper;

    @Override
    public Room createRequestToEntity(CreateRoomRequest createRoomRequest) {

        Room room = new Room();

        if ( createRoomRequest != null ) {
            if ( createRoomRequest.getTeacherCreateRoom() != null ) {
                room.setTeacher( teacherMapper.createRoomToEntity( createRoomRequest.getTeacherCreateRoom() ) );
            }
            if ( createRoomRequest.getName() != null ) {
                room.setName( createRoomRequest.getName() );
            }
            room.setNumberOfStudent( createRoomRequest.getNumberOfStudent() );
            if ( createRoomRequest.getPricePerLesson() != null ) {
                room.setPricePerLesson( createRoomRequest.getPricePerLesson() );
            }
        }

        return room;
    }

    @Override
    public Room updateRequestToEntity(UpdateRoomRequest updateRoomRequest) {

        Room room = new Room();

        if ( updateRoomRequest != null ) {
            List<Student> list = studentMapper.listUpdateInRoomToEntity( updateRoomRequest.getUpdateStudentInRoomRequests() );
            if ( list != null ) {
                room.setStudents( list );
            }
            if ( updateRoomRequest.getTeacherCreateRoom() != null ) {
                room.setTeacher( teacherMapper.createRoomToEntity( updateRoomRequest.getTeacherCreateRoom() ) );
            }
            if ( updateRoomRequest.getId() != null ) {
                room.setId( updateRoomRequest.getId() );
            }
            if ( updateRoomRequest.getName() != null ) {
                room.setName( updateRoomRequest.getName() );
            }
            if ( updateRoomRequest.getStartDate() != null ) {
                room.setStartDate( LocalDateTime.ofInstant( updateRoomRequest.getStartDate().toInstant(), ZoneOffset.UTC ).toLocalDate() );
            }
            room.setNumberOfStudent( updateRoomRequest.getNumberOfStudent() );
            if ( updateRoomRequest.getState() != null ) {
                room.setState( updateRoomRequest.getState() );
            }
            if ( updateRoomRequest.getPricePerLesson() != null ) {
                room.setPricePerLesson( updateRoomRequest.getPricePerLesson() );
            }
        }

        return room;
    }

    @Override
    public RoomResponse entityToResponse(Room room) {

        RoomResponse roomResponse = new RoomResponse();

        if ( room != null ) {
            if ( room.getId() != null ) {
                roomResponse.setId( room.getId() );
            }
            if ( room.getName() != null ) {
                roomResponse.setName( room.getName() );
            }
            if ( room.getStartDate() != null ) {
                roomResponse.setStartDate( Date.from( room.getStartDate().atStartOfDay( ZoneOffset.UTC ).toInstant() ) );
            }
            if ( room.getEndDate() != null ) {
                roomResponse.setEndDate( Date.from( room.getEndDate().atStartOfDay( ZoneOffset.UTC ).toInstant() ) );
            }
            roomResponse.setNumberOfStudent( room.getNumberOfStudent() );
            if ( room.getState() != null ) {
                roomResponse.setState( room.getState() );
            }
            if ( room.getPricePerLesson() != null ) {
                roomResponse.setPricePerLesson( room.getPricePerLesson() );
            }
        }

        return roomResponse;
    }

    @Override
    public RoomCreateResponse entityToCreateResponse(Room room) {

        RoomCreateResponse roomCreateResponse = new RoomCreateResponse();

        if ( room != null ) {
            if ( room.getId() != null ) {
                roomCreateResponse.setId( room.getId() );
            }
            if ( room.getName() != null ) {
                roomCreateResponse.setName( room.getName() );
            }
            if ( room.getStartDate() != null ) {
                roomCreateResponse.setStartDate( Date.from( room.getStartDate().atStartOfDay( ZoneOffset.UTC ).toInstant() ) );
            }
            roomCreateResponse.setNumberOfStudent( room.getNumberOfStudent() );
            if ( room.getState() != null ) {
                roomCreateResponse.setState( room.getState() );
            }
            if ( room.getPricePerLesson() != null ) {
                roomCreateResponse.setPricePerLesson( room.getPricePerLesson() );
            }
        }

        return roomCreateResponse;
    }

    @Override
    public RoomShortResponse entityToShortResponse(Room room) {

        RoomShortResponse roomShortResponse = new RoomShortResponse();

        if ( room != null ) {
            if ( room.getId() != null ) {
                roomShortResponse.setId( room.getId() );
            }
            if ( room.getName() != null ) {
                roomShortResponse.setName( room.getName() );
            }
            if ( room.getStartDate() != null ) {
                roomShortResponse.setStartDate( Date.from( room.getStartDate().atStartOfDay( ZoneOffset.UTC ).toInstant() ) );
            }
            roomShortResponse.setNumberOfStudent( room.getNumberOfStudent() );
            if ( room.getState() != null ) {
                roomShortResponse.setState( room.getState() );
            }
            if ( room.getPricePerLesson() != null ) {
                roomShortResponse.setPricePerLesson( room.getPricePerLesson() );
            }
        }

        return roomShortResponse;
    }

    @Override
    public List<RoomShortResponse> listEntityToShortResponse(List<Room> rooms) {
        if ( rooms == null ) {
            return new ArrayList<RoomShortResponse>();
        }

        List<RoomShortResponse> list = new ArrayList<RoomShortResponse>( rooms.size() );
        for ( Room room : rooms ) {
            list.add( entityToShortResponse( room ) );
        }

        return list;
    }

    @Override
    public RoomUpdateResponse entityToUpdateResponse(Room room) {

        RoomUpdateResponse roomUpdateResponse = new RoomUpdateResponse();

        if ( room != null ) {
            List<StudentShortResponse> list = studentMapper.listEntityToInRoomResponse( room.getStudents() );
            if ( list != null ) {
                roomUpdateResponse.setStudentShortResponses( list );
            }
            if ( room.getId() != null ) {
                roomUpdateResponse.setId( room.getId() );
            }
            if ( room.getName() != null ) {
                roomUpdateResponse.setName( room.getName() );
            }
            if ( room.getStartDate() != null ) {
                roomUpdateResponse.setStartDate( Date.from( room.getStartDate().atStartOfDay( ZoneOffset.UTC ).toInstant() ) );
            }
            roomUpdateResponse.setNumberOfStudent( room.getNumberOfStudent() );
            if ( room.getState() != null ) {
                roomUpdateResponse.setState( room.getState() );
            }
            if ( room.getPricePerLesson() != null ) {
                roomUpdateResponse.setPricePerLesson( room.getPricePerLesson() );
            }
        }

        return roomUpdateResponse;
    }

    @Override
    public List<RoomResponse> listEntityToResponse(List<Room> rooms) {
        if ( rooms == null ) {
            return new ArrayList<RoomResponse>();
        }

        List<RoomResponse> list = new ArrayList<RoomResponse>( rooms.size() );
        for ( Room room : rooms ) {
            list.add( entityToResponse( room ) );
        }

        return list;
    }

    @Override
    public RoomAttendanceResponse entityToRoomAttendance(Room room) {

        RoomAttendanceResponse roomAttendanceResponse = new RoomAttendanceResponse();

        if ( room != null ) {
            if ( room.getId() != null ) {
                roomAttendanceResponse.setId( room.getId() );
            }
            if ( room.getName() != null ) {
                roomAttendanceResponse.setName( room.getName() );
            }
        }

        return roomAttendanceResponse;
    }

    @Override
    public RoomCostResponse entityToCostResponse(Room room) {

        RoomCostResponse roomCostResponse = new RoomCostResponse();

        if ( room != null ) {
            if ( room.getId() != null ) {
                roomCostResponse.setId( room.getId() );
            }
            if ( room.getName() != null ) {
                roomCostResponse.setName( room.getName() );
            }
        }

        return roomCostResponse;
    }
}
