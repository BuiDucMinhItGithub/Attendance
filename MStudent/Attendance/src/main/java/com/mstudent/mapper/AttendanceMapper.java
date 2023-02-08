package com.mstudent.mapper;

import com.mstudent.model.dto.response.Attendance.AttendanceResponse;
import com.mstudent.model.entity.Attendance;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AttendanceMapper {
    AttendanceMapper INSTANCE = Mappers.getMapper(AttendanceMapper.class);

    AttendanceResponse entityToResponse(Attendance attendance);

    List<AttendanceResponse> listEntityToResponse(List<Attendance> attendances);

}
