package com.mstudent.mapper;

import com.mstudent.model.dto.request.CreateStudentRequest;
import com.mstudent.model.dto.request.UpdateStudentRequest;
import com.mstudent.model.entity.Student;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface StudentMapper {
    StudentMapper INSTANCE = Mappers.getMapper(StudentMapper.class);

    Student createRequestToEntity(CreateStudentRequest createStudentRequest);

    Student updateRequestToEntity(UpdateStudentRequest updateStudentRequest);

}