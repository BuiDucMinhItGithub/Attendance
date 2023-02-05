package com.mstudent.mapper;

import com.mstudent.model.dto.request.CreateRoomRequest;
import com.mstudent.model.dto.request.UpdateRoomRequest;
import com.mstudent.model.entity.Room;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RoomMapper {
    RoomMapper INSTANCE = Mappers.getMapper(RoomMapper.class);

    Room createRequestToEntity(CreateRoomRequest createRoomRequest);

    Room updateRequestToEntity(UpdateRoomRequest updateRoomRequest);
}
