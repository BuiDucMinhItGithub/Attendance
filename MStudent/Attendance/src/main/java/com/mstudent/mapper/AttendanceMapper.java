package com.mstudent.mapper;

import com.mstudent.model.dto.request.CreateAttendanceRequest;
import com.mstudent.model.entity.Attendance;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AttendanceMapper {
    AttendanceMapper INSTANCE = Mappers.getMapper(AttendanceMapper.class);

}
