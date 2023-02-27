package com.mstudent.mapper;

import com.mstudent.model.dto.request.Student.CreateStudentRequest;
import com.mstudent.model.dto.request.Student.UpdateStudentInRoomRequest;
import com.mstudent.model.dto.request.Student.UpdateStudentRequest;
import com.mstudent.model.dto.response.Student.StudentFullResponse;
import com.mstudent.model.dto.response.Student.StudentResponse;
import com.mstudent.model.dto.response.Student.StudentShortResponse;
import com.mstudent.model.entity.Student;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-02-27T10:45:24+0700",
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
                student.setBirthday( LocalDateTime.ofInstant( createStudentRequest.getBirthday().toInstant(), ZoneOffset.UTC ).toLocalDate() );
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
                student.setBirthday( LocalDateTime.ofInstant( updateStudentRequest.getBirthday().toInstant(), ZoneOffset.UTC ).toLocalDate() );
            }
            if ( updateStudentRequest.getState() != null ) {
                student.setState( updateStudentRequest.getState() );
            }
        }

        return student;
    }

    @Override
    public StudentFullResponse entityToResponse(Student student) {

        StudentFullResponse studentFullResponse = new StudentFullResponse();

        if ( student != null ) {
            if ( student.getId() != null ) {
                studentFullResponse.setId( student.getId() );
            }
            if ( student.getFullName() != null ) {
                studentFullResponse.setFullName( student.getFullName() );
            }
            if ( student.getBirthday() != null ) {
                studentFullResponse.setBirthday( Date.from( student.getBirthday().atStartOfDay( ZoneOffset.UTC ).toInstant() ) );
            }
            if ( student.getState() != null ) {
                studentFullResponse.setState( student.getState() );
            }
        }

        return studentFullResponse;
    }

    @Override
    public List<StudentFullResponse> listEntityToResponse(List<Student> students) {
        if ( students == null ) {
            return new ArrayList<StudentFullResponse>();
        }

        List<StudentFullResponse> list = new ArrayList<StudentFullResponse>( students.size() );
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

    @Override
    public StudentShortResponse entityToShortResponse(Student student) {

        StudentShortResponse studentShortResponse = new StudentShortResponse();

        if ( student != null ) {
            if ( student.getId() != null ) {
                studentShortResponse.setId( student.getId() );
            }
            if ( student.getFullName() != null ) {
                studentShortResponse.setFullName( student.getFullName() );
            }
        }

        return studentShortResponse;
    }

    @Override
    public List<StudentShortResponse> listEntityToShortResponse(List<Student> students) {
        if ( students == null ) {
            return new ArrayList<StudentShortResponse>();
        }

        List<StudentShortResponse> list = new ArrayList<StudentShortResponse>( students.size() );
        for ( Student student : students ) {
            list.add( studentToStudentShortResponse( student ) );
        }

        return list;
    }

    @Override
    public List<StudentShortResponse> listEntityToInRoomResponse(List<Student> students) {
        if ( students == null ) {
            return new ArrayList<StudentShortResponse>();
        }

        List<StudentShortResponse> list = new ArrayList<StudentShortResponse>( students.size() );
        for ( Student student : students ) {
            list.add( studentToStudentShortResponse( student ) );
        }

        return list;
    }

    @Override
    public StudentResponse entityToResponseUpdateAndInsert(Student student) {

        StudentResponse studentResponse = new StudentResponse();

        if ( student != null ) {
            if ( student.getId() != null ) {
                studentResponse.setId( student.getId() );
            }
            if ( student.getFullName() != null ) {
                studentResponse.setFullName( student.getFullName() );
            }
            if ( student.getBirthday() != null ) {
                studentResponse.setBirthday( Date.from( student.getBirthday().atStartOfDay( ZoneOffset.UTC ).toInstant() ) );
            }
            if ( student.getState() != null ) {
                studentResponse.setState( student.getState() );
            }
        }

        return studentResponse;
    }

    protected StudentShortResponse studentToStudentShortResponse(Student student) {

        StudentShortResponse studentShortResponse = new StudentShortResponse();

        if ( student != null ) {
            if ( student.getId() != null ) {
                studentShortResponse.setId( student.getId() );
            }
            if ( student.getFullName() != null ) {
                studentShortResponse.setFullName( student.getFullName() );
            }
        }

        return studentShortResponse;
    }
}
