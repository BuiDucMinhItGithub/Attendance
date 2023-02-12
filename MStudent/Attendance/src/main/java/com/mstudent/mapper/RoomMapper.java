package com.mstudent.mapper;

import com.mstudent.model.dto.request.Room.CreateRoomRequest;
import com.mstudent.model.dto.request.Room.UpdateRoomRequest;
import com.mstudent.model.dto.response.Room.*;
import com.mstudent.model.entity.Room;
import java.util.List;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper( componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
    nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
    collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
    uses = {StudentMapper.class})
public interface RoomMapper {
    RoomMapper INSTANCE = Mappers.getMapper(RoomMapper.class);

    Room createRequestToEntity(CreateRoomRequest createRoomRequest);

    @Mapping(target = "students", qualifiedByName = "listUpdateInRoomToEntity")
    Room updateRequestToEntity(UpdateRoomRequest updateRoomRequest);

    RoomResponse entityToResponse(Room room);

    RoomCreateResponse entityToCreateResponse(Room room);


    RoomShortResponse entityToShortResponse(Room room);

    List<RoomShortResponse> listEntityToShortResponse(List<Room> rooms);

    @Mapping(target = "students", qualifiedByName = "listEntityToInRoomResponse")
    RoomUpdateResponse entityToUpdateResponse(Room room);

    List<RoomResponse> listEntityToResponse(List<Room> rooms);
    @Named("entityToRoomAttendance")
    RoomAttendanceResponse entityToRoomAttendance(Room room);

    @Named("entityToCostResponse")
    RoomCostResponse entityToCostResponse(Room room);
}
