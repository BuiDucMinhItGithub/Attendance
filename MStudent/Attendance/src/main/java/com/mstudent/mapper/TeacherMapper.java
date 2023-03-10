package com.mstudent.mapper;

import com.mstudent.model.dto.request.Teacher.CreateTeacherRequest;
import com.mstudent.model.dto.request.Teacher.TeacherCreateRoom;
import com.mstudent.model.dto.request.Teacher.UpdateTeacherRequest;
import com.mstudent.model.dto.response.Teacher.TeacherRegisterResponse;
import com.mstudent.model.dto.response.Teacher.TeacherResponse;
import com.mstudent.model.entity.Teacher;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper( componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
    nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
    collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
    uses = {RoomMapper.class, StudentMapper.class})
public interface TeacherMapper {
    TeacherMapper INSTANCE = Mappers.getMapper(TeacherMapper.class);

    Teacher createRequestToEntity(CreateTeacherRequest createTeacherRequest);
    @Named("createRoomToEntity")
    Teacher createRoomToEntity(TeacherCreateRoom teacherCreateRoom);

    Teacher updateRequestToEntity(UpdateTeacherRequest updateTeacherRequest);

    TeacherResponse entityToResponse(Teacher teacher);

    TeacherRegisterResponse entityToRegisterResponse(Teacher teacher);

    List<TeacherResponse> listEntityToResponse(List<Teacher> teachers);
}
