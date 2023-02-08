package com.mstudent.mapper;

import com.mstudent.model.dto.request.Student.CreateStudentRequest;
import com.mstudent.model.dto.request.Student.UpdateStudentRequest;
import com.mstudent.model.dto.response.Student.StudentResponse;
import com.mstudent.model.entity.Student;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-02-08T15:23:26+0700",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17.0.5 (Oracle Corporation)"
)
@Component
public class StudentMapperImpl implements StudentMapper {

    @Override
    public Student createRequestToEntity(CreateStudentRequest createStudentRequest) {
        if ( createStudentRequest == null ) {
            return null;
        }

        Student student = new Student();

        student.setFullName( createStudentRequest.getFullName() );
        student.setBirthday( createStudentRequest.getBirthday() );

        return student;
    }

    @Override
    public Student updateRequestToEntity(UpdateStudentRequest updateStudentRequest) {
        if ( updateStudentRequest == null ) {
            return null;
        }

        Student student = new Student();

        student.setId( updateStudentRequest.getId() );
        student.setFullName( updateStudentRequest.getFullName() );
        student.setBirthday( updateStudentRequest.getBirthday() );
        student.setState( updateStudentRequest.getState() );

        return student;
    }

    @Override
    public StudentResponse entityToResponse(Student student) {
        if ( student == null ) {
            return null;
        }

        StudentResponse studentResponse = new StudentResponse();

        studentResponse.setId( student.getId() );
        studentResponse.setFullName( student.getFullName() );
        studentResponse.setBirthday( student.getBirthday() );
        studentResponse.setState( student.getState() );

        return studentResponse;
    }

    @Override
    public List<StudentResponse> listEntityToResponse(List<Student> students) {
        if ( students == null ) {
            return null;
        }

        List<StudentResponse> list = new ArrayList<StudentResponse>( students.size() );
        for ( Student student : students ) {
            list.add( entityToResponse( student ) );
        }

        return list;
    }
}
