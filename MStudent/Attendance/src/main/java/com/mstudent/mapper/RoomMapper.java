package com.mstudent.mapper;

import com.mstudent.model.dto.request.Room.CreateRoomRequest;
import com.mstudent.model.dto.request.Room.UpdateRoomRequest;
import com.mstudent.model.dto.response.Room.RoomResponse;
import com.mstudent.model.entity.Room;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RoomMapper {
    RoomMapper INSTANCE = Mappers.getMapper(RoomMapper.class);

    Room createRequestToEntity(CreateRoomRequest createRoomRequest);

    Room updateRequestToEntity(UpdateRoomRequest updateRoomRequest);

    RoomResponse entityToResponse(Room room);

    List<RoomResponse> listEntityToResponse(List<Room> rooms);
}
