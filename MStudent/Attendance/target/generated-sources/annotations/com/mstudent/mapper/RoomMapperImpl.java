package com.mstudent.mapper;

import com.mstudent.model.dto.request.Room.CreateRoomRequest;
import com.mstudent.model.dto.request.Room.UpdateRoomRequest;
import com.mstudent.model.dto.response.Room.RoomCreateResponse;
import com.mstudent.model.dto.response.Room.RoomResponse;
import com.mstudent.model.dto.response.Room.RoomUpdateResponse;
import com.mstudent.model.dto.response.Student.StudentInRoomResponse;
import com.mstudent.model.entity.Room;
import com.mstudent.model.entity.Student;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-02-10T15:50:43+0700",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17.0.5 (Oracle Corporation)"
)
@Component
public class RoomMapperImpl implements RoomMapper {

    @Autowired
    private StudentMapper studentMapper;

    @Override
    public Room createRequestToEntity(CreateRoomRequest createRoomRequest) {

        Room room = new Room();

        if ( createRoomRequest != null ) {
            if ( createRoomRequest.getName() != null ) {
                room.setName( createRoomRequest.getName() );
            }
            if ( createRoomRequest.getTeacher() != null ) {
                room.setTeacher( createRoomRequest.getTeacher() );
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
            List<Student> list = studentMapper.listUpdateInRoomToEntity( updateRoomRequest.getStudents() );
            if ( list != null ) {
                room.setStudents( list );
            }
            if ( updateRoomRequest.getId() != null ) {
                room.setId( updateRoomRequest.getId() );
            }
            if ( updateRoomRequest.getName() != null ) {
                room.setName( updateRoomRequest.getName() );
            }
            if ( updateRoomRequest.getStartDate() != null ) {
                room.setStartDate( updateRoomRequest.getStartDate() );
            }
            if ( updateRoomRequest.getTeacher() != null ) {
                room.setTeacher( updateRoomRequest.getTeacher() );
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
            if ( room.getStartDate() != null ) {
                roomResponse.setStartDate( room.getStartDate() );
            }
            if ( room.getEndDate() != null ) {
                roomResponse.setEndDate( room.getEndDate() );
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
            if ( room.getStartDate() != null ) {
                roomCreateResponse.setStartDate( room.getStartDate() );
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
    public RoomUpdateResponse entityToUpdateResponse(Room room) {

        RoomUpdateResponse roomUpdateResponse = new RoomUpdateResponse();

        if ( room != null ) {
            List<StudentInRoomResponse> list = studentMapper.listEntityToInRoomResponse( room.getStudents() );
            if ( list != null ) {
                roomUpdateResponse.setStudents( list );
            }
            if ( room.getId() != null ) {
                roomUpdateResponse.setId( room.getId() );
            }
            if ( room.getStartDate() != null ) {
                roomUpdateResponse.setStartDate( room.getStartDate() );
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
}
