package com.mstudent.mapper;

import com.mstudent.model.dto.request.Room.CreateRoomRequest;
import com.mstudent.model.dto.request.Room.UpdateRoomRequest;
import com.mstudent.model.dto.response.Room.RoomResponse;
import com.mstudent.model.entity.Room;
import com.mstudent.model.entity.Student;
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
public class RoomMapperImpl implements RoomMapper {

    @Override
    public Room createRequestToEntity(CreateRoomRequest createRoomRequest) {
        if ( createRoomRequest == null ) {
            return null;
        }

        Room room = new Room();

        room.setName( createRoomRequest.getName() );
        room.setStartDate( createRoomRequest.getStartDate() );
        room.setTeacher( createRoomRequest.getTeacher() );
        room.setNumberOfStudent( createRoomRequest.getNumberOfStudent() );
        room.setPricePerLesson( createRoomRequest.getPricePerLesson() );
        List<Student> list = createRoomRequest.getStudents();
        if ( list != null ) {
            room.setStudents( new ArrayList<Student>( list ) );
        }

        return room;
    }

    @Override
    public Room updateRequestToEntity(UpdateRoomRequest updateRoomRequest) {
        if ( updateRoomRequest == null ) {
            return null;
        }

        Room room = new Room();

        room.setId( updateRoomRequest.getId() );
        room.setName( updateRoomRequest.getName() );
        room.setStartDate( updateRoomRequest.getStartDate() );
        room.setTeacher( updateRoomRequest.getTeacher() );
        room.setNumberOfStudent( updateRoomRequest.getNumberOfStudent() );
        room.setState( updateRoomRequest.getState() );
        room.setPricePerLesson( updateRoomRequest.getPricePerLesson() );
        List<Student> list = updateRoomRequest.getStudents();
        if ( list != null ) {
            room.setStudents( new ArrayList<Student>( list ) );
        }

        return room;
    }

    @Override
    public RoomResponse entityToResponse(Room room) {
        if ( room == null ) {
            return null;
        }

        RoomResponse roomResponse = new RoomResponse();

        roomResponse.setId( room.getId() );
        roomResponse.setStartDate( room.getStartDate() );
        roomResponse.setEndDate( room.getEndDate() );
        roomResponse.setNumberOfStudent( room.getNumberOfStudent() );
        roomResponse.setState( room.getState() );
        roomResponse.setPricePerLesson( room.getPricePerLesson() );

        return roomResponse;
    }

    @Override
    public List<RoomResponse> listEntityToResponse(List<Room> rooms) {
        if ( rooms == null ) {
            return null;
        }

        List<RoomResponse> list = new ArrayList<RoomResponse>( rooms.size() );
        for ( Room room : rooms ) {
            list.add( entityToResponse( room ) );
        }

        return list;
    }
}
