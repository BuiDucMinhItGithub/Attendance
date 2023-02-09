package com.mstudent.mapper;

import com.mstudent.model.dto.request.Student.CreateStudentRequest;
import com.mstudent.model.dto.request.Student.UpdateStudentInRoomRequest;
import com.mstudent.model.dto.request.Student.UpdateStudentRequest;
import com.mstudent.model.dto.response.Student.StudentResponse;
import com.mstudent.model.entity.Student;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-02-09T17:09:16+0700",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17.0.5 (Oracle Corporation)"
)
@Component
public class StudentMapperImpl implements StudentMapper {

    @Override
    public Student createRequestToEntity(CreateStudentRequest createStudentRequest) {

        Student student = new Student();

        if ( createStudentRequest != null ) {
            if ( createStudentRequest.getFullName() != null ) {
                student.setFullName( createStudentRequest.getFullName() );
            }
            if ( createStudentRequest.getBirthday() != null ) {
                student.setBirthday( createStudentRequest.getBirthday() );
            }
        }

        return student;
    }

    @Override
    public Student updateRequestToEntity(UpdateStudentRequest updateStudentRequest) {

        Student student = new Student();

        if ( updateStudentRequest != null ) {
            if ( updateStudentRequest.getId() != null ) {
                student.setId( updateStudentRequest.getId() );
            }
            if ( updateStudentRequest.getFullName() != null ) {
                student.setFullName( updateStudentRequest.getFullName() );
            }
            if ( updateStudentRequest.getBirthday() != null ) {
                student.setBirthday( updateStudentRequest.getBirthday() );
            }
            if ( updateStudentRequest.getState() != null ) {
                student.setState( updateStudentRequest.getState() );
            }
        }

        return student;
    }

    @Override
    public StudentResponse entityToResponse(Student student) {

        StudentResponse studentResponse = new StudentResponse();

        if ( student != null ) {
            if ( student.getId() != null ) {
                studentResponse.setId( student.getId() );
            }
            if ( student.getFullName() != null ) {
                studentResponse.setFullName( student.getFullName() );
            }
            if ( student.getBirthday() != null ) {
                studentResponse.setBirthday( student.getBirthday() );
            }
            if ( student.getState() != null ) {
                studentResponse.setState( student.getState() );
            }
        }

        return studentResponse;
    }

    @Override
    public List<StudentResponse> listEntityToResponse(List<Student> students) {
        if ( students == null ) {
            return new ArrayList<StudentResponse>();
        }

        List<StudentResponse> list = new ArrayList<StudentResponse>( students.size() );
        for ( Student student : students ) {
            list.add( entityToResponse( student ) );
        }

        return list;
    }

    @Override
    public Student updateInRoomToEntity(UpdateStudentInRoomRequest updateStudentInRoomRequests) {

        Student student = new Student();

        if ( updateStudentInRoomRequests != null ) {
            if ( updateStudentInRoomRequests.getId() != null ) {
                student.setId( updateStudentInRoomRequests.getId() );
            }
            if ( updateStudentInRoomRequests.getFullName() != null ) {
                student.setFullName( updateStudentInRoomRequests.getFullName() );
            }
        }

        return student;
    }

    @Override
    public List<Student> listUpdateInRoomToEntity(List<UpdateStudentInRoomRequest> updateStudentInRoomRequests) {
        if ( updateStudentInRoomRequests == null ) {
            return new ArrayList<Student>();
        }

        List<Student> list = new ArrayList<Student>( updateStudentInRoomRequests.size() );
        for ( UpdateStudentInRoomRequest updateStudentInRoomRequest : updateStudentInRoomRequests ) {
            list.add( updateInRoomToEntity( updateStudentInRoomRequest ) );
        }

        return list;
    }
}
