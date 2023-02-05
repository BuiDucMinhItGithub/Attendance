package com.mstudent.mapper;

import com.mstudent.model.dto.request.CreateStudentRequest;
import com.mstudent.model.dto.request.UpdateStudentRequest;
import com.mstudent.model.entity.Student;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-02-05T00:21:18+0700",
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
}
