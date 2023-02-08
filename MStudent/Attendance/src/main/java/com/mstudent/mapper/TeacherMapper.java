package com.mstudent.mapper;

import com.mstudent.model.dto.request.Teacher.CreateTeacherRequest;
import com.mstudent.model.dto.request.Teacher.UpdateTeacherRequest;
import com.mstudent.model.entity.Teacher;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TeacherMapper {
    TeacherMapper INSTANCE = Mappers.getMapper(TeacherMapper.class);

    Teacher createRequestToEntity(CreateTeacherRequest createTeacherRequest);

    Teacher updateRequestToEntity(UpdateTeacherRequest updateTeacherRequest);
}
