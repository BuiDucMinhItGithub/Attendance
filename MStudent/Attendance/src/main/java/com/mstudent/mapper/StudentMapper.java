package com.mstudent.mapper;

import com.mstudent.model.dto.request.Student.CreateStudentRequest;
import com.mstudent.model.dto.request.Student.UpdateStudentInRoomRequest;
import com.mstudent.model.dto.request.Student.UpdateStudentRequest;
import com.mstudent.model.dto.response.Student.StudentResponse;
import com.mstudent.model.dto.response.Student.StudentShortResponse;
import com.mstudent.model.dto.response.Student.StudentFullResponse;
import com.mstudent.model.entity.Student;
import java.util.List;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
    nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
    collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED)
public interface StudentMapper {
    StudentMapper INSTANCE = Mappers.getMapper(StudentMapper.class);

    Student createRequestToEntity(CreateStudentRequest createStudentRequest);

    Student updateRequestToEntity(UpdateStudentRequest updateStudentRequest);

    StudentFullResponse entityToResponse(Student student);

    List<StudentFullResponse> listEntityToResponse(List<Student> students);

    Student updateInRoomToEntity(UpdateStudentInRoomRequest updateStudentInRoomRequests);
    @Named("listUpdateInRoomToEntity")
    List<Student> listUpdateInRoomToEntity(List<UpdateStudentInRoomRequest> updateStudentInRoomRequests);

    @Named("entityToShortResponse")
    StudentShortResponse entityToShortResponse(Student student);

    List<StudentShortResponse> listEntityToShortResponse(List<Student> students);

    @Named("listEntityToInRoomResponse")
    List<StudentShortResponse> listEntityToInRoomResponse(List<Student> students);

    StudentResponse entityToResponseUpdateAndInsert(Student student);

}
