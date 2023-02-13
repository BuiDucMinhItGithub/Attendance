package com.mstudent.mapper;

import com.mstudent.model.dto.request.Teacher.CreateTeacherRequest;
import com.mstudent.model.dto.request.Teacher.UpdateTeacherRequest;
import com.mstudent.model.dto.response.Teacher.TeacherRegisterResponse;
import com.mstudent.model.dto.response.Teacher.TeacherResponse;
import com.mstudent.model.entity.Teacher;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-02-13T17:19:21+0700",
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

    @Override
    public TeacherResponse entityToResponse(Teacher teacher) {
        if ( teacher == null ) {
            return null;
        }

        TeacherResponse teacherResponse = new TeacherResponse();

        teacherResponse.setId( teacher.getId() );
        teacherResponse.setFullName( teacher.getFullName() );
        teacherResponse.setUserName( teacher.getUserName() );
        teacherResponse.setBirthday( teacher.getBirthday() );
        teacherResponse.setPassword( teacher.getPassword() );
        teacherResponse.setPhone( teacher.getPhone() );
        teacherResponse.setAddress( teacher.getAddress() );
        teacherResponse.setState( teacher.getState() );
        teacherResponse.setRole( teacher.getRole() );

        return teacherResponse;
    }

    @Override
    public TeacherRegisterResponse entityToRegisterResponse(Teacher teacher) {
        if ( teacher == null ) {
            return null;
        }

        TeacherRegisterResponse teacherRegisterResponse = new TeacherRegisterResponse();

        teacherRegisterResponse.setId( teacher.getId() );
        teacherRegisterResponse.setFullName( teacher.getFullName() );
        teacherRegisterResponse.setUserName( teacher.getUserName() );
        teacherRegisterResponse.setBirthday( teacher.getBirthday() );
        teacherRegisterResponse.setPassword( teacher.getPassword() );
        teacherRegisterResponse.setPhone( teacher.getPhone() );
        teacherRegisterResponse.setAddress( teacher.getAddress() );
        teacherRegisterResponse.setState( teacher.getState() );
        teacherRegisterResponse.setRole( teacher.getRole() );

        return teacherRegisterResponse;
    }

    @Override
    public List<TeacherResponse> listEntityToResponse(List<Teacher> teachers) {
        if ( teachers == null ) {
            return null;
        }

        List<TeacherResponse> list = new ArrayList<TeacherResponse>( teachers.size() );
        for ( Teacher teacher : teachers ) {
            list.add( entityToResponse( teacher ) );
        }

        return list;
    }
}
