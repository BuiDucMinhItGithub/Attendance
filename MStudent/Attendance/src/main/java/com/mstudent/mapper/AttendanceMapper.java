package com.mstudent.mapper;

import com.mstudent.model.dto.response.Attendance.AttendanceResponse;
import com.mstudent.model.entity.Attendance;
import java.util.List;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper( componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
        uses = {RoomMapper.class, StudentMapper.class})
public interface AttendanceMapper {
    AttendanceMapper INSTANCE = Mappers.getMapper(AttendanceMapper.class);
    @Mapping(target = "roomAttendanceResponse", source = "room", qualifiedByName = "entityToRoomAttendance")
    AttendanceResponse entityToResponse(Attendance attendance);

    List<AttendanceResponse> listEntityToResponse(List<Attendance> attendances);

}
