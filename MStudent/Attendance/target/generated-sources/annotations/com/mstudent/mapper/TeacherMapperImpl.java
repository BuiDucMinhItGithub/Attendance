package com.mstudent.mapper;

import com.mstudent.model.dto.request.CreateTeacherRequest;
import com.mstudent.model.dto.request.UpdateTeacherRequest;
import com.mstudent.model.entity.Teacher;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-02-05T00:21:18+0700",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17.0.5 (Oracle Corporation)"
)
@Component
public class TeacherMapperImpl implements TeacherMapper {

    @Override
    public Teacher createRequestToEntity(CreateTeacherRequest createTeacherRequest) {
        if ( createTeacherRequest == null ) {
            return null;
        }

        Teacher teacher = new Teacher();

        teacher.setFullName( createTeacherRequest.getFullName() );
        teacher.setUserName( createTeacherRequest.getUserName() );
        teacher.setBirthday( createTeacherRequest.getBirthday() );
        teacher.setPassword( createTeacherRequest.getPassword() );
        teacher.setPhone( createTeacherRequest.getPhone() );
        teacher.setAddress( createTeacherRequest.getAddress() );

        return teacher;
    }

    @Override
    public Teacher updateRequestToEntity(UpdateTeacherRequest updateTeacherRequest) {
        if ( updateTeacherRequest == null ) {
            return null;
        }

        Teacher teacher = new Teacher();

        teacher.setId( updateTeacherRequest.getId() );
        teacher.setFullName( updateTeacherRequest.getFullName() );
        teacher.setUserName( updateTeacherRequest.getUserName() );
        teacher.setBirthday( updateTeacherRequest.getBirthday() );
        teacher.setPassword( updateTeacherRequest.getPassword() );
        teacher.setPhone( updateTeacherRequest.getPhone() );
        teacher.setAddress( updateTeacherRequest.getAddress() );
        teacher.setState( updateTeacherRequest.getState() );

        return teacher;
    }
}
