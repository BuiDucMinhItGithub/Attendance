package com.mstudent.mapper;

import com.mstudent.model.dto.request.Teacher.CreateTeacherRequest;
import com.mstudent.model.dto.request.Teacher.TeacherCreateRoom;
import com.mstudent.model.dto.request.Teacher.UpdateTeacherRequest;
import com.mstudent.model.dto.response.Teacher.TeacherRegisterResponse;
import com.mstudent.model.dto.response.Teacher.TeacherResponse;
import com.mstudent.model.entity.Teacher;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-02-26T00:02:37+0700",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17.0.5 (Oracle Corporation)"
)
@Component
public class TeacherMapperImpl implements TeacherMapper {

    @Override
    public Teacher createRequestToEntity(CreateTeacherRequest createTeacherRequest) {

        Teacher teacher = new Teacher();

        if ( createTeacherRequest != null ) {
            if ( createTeacherRequest.getFullName() != null ) {
                teacher.setFullName( createTeacherRequest.getFullName() );
            }
            if ( createTeacherRequest.getUserName() != null ) {
                teacher.setUserName( createTeacherRequest.getUserName() );
            }
            if ( createTeacherRequest.getBirthday() != null ) {
                teacher.setBirthday( LocalDateTime.ofInstant( createTeacherRequest.getBirthday().toInstant(), ZoneOffset.UTC ).toLocalDate() );
            }
            if ( createTeacherRequest.getPassword() != null ) {
                teacher.setPassword( createTeacherRequest.getPassword() );
            }
            if ( createTeacherRequest.getPhone() != null ) {
                teacher.setPhone( createTeacherRequest.getPhone() );
            }
            if ( createTeacherRequest.getAddress() != null ) {
                teacher.setAddress( createTeacherRequest.getAddress() );
            }
        }

        return teacher;
    }

    @Override
    public Teacher createRoomToEntity(TeacherCreateRoom teacherCreateRoom) {

        Teacher teacher = new Teacher();

        if ( teacherCreateRoom != null ) {
            if ( teacherCreateRoom.getId() != null ) {
                teacher.setId( teacherCreateRoom.getId() );
            }
        }

        return teacher;
    }

    @Override
    public Teacher updateRequestToEntity(UpdateTeacherRequest updateTeacherRequest) {

        Teacher teacher = new Teacher();

        if ( updateTeacherRequest != null ) {
            if ( updateTeacherRequest.getId() != null ) {
                teacher.setId( updateTeacherRequest.getId() );
            }
            if ( updateTeacherRequest.getFullName() != null ) {
                teacher.setFullName( updateTeacherRequest.getFullName() );
            }
            if ( updateTeacherRequest.getUserName() != null ) {
                teacher.setUserName( updateTeacherRequest.getUserName() );
            }
            if ( updateTeacherRequest.getBirthday() != null ) {
                teacher.setBirthday( LocalDateTime.ofInstant( updateTeacherRequest.getBirthday().toInstant(), ZoneOffset.UTC ).toLocalDate() );
            }
            if ( updateTeacherRequest.getPassword() != null ) {
                teacher.setPassword( updateTeacherRequest.getPassword() );
            }
            if ( updateTeacherRequest.getPhone() != null ) {
                teacher.setPhone( updateTeacherRequest.getPhone() );
            }
            if ( updateTeacherRequest.getAddress() != null ) {
                teacher.setAddress( updateTeacherRequest.getAddress() );
            }
            if ( updateTeacherRequest.getState() != null ) {
                teacher.setState( updateTeacherRequest.getState() );
            }
        }

        return teacher;
    }

    @Override
    public TeacherResponse entityToResponse(Teacher teacher) {

        TeacherResponse teacherResponse = new TeacherResponse();

        if ( teacher != null ) {
            if ( teacher.getId() != null ) {
                teacherResponse.setId( teacher.getId() );
            }
            if ( teacher.getFullName() != null ) {
                teacherResponse.setFullName( teacher.getFullName() );
            }
            if ( teacher.getUserName() != null ) {
                teacherResponse.setUserName( teacher.getUserName() );
            }
            if ( teacher.getBirthday() != null ) {
                teacherResponse.setBirthday( Date.from( teacher.getBirthday().atStartOfDay( ZoneOffset.UTC ).toInstant() ) );
            }
            if ( teacher.getPassword() != null ) {
                teacherResponse.setPassword( teacher.getPassword() );
            }
            if ( teacher.getPhone() != null ) {
                teacherResponse.setPhone( teacher.getPhone() );
            }
            if ( teacher.getAddress() != null ) {
                teacherResponse.setAddress( teacher.getAddress() );
            }
            if ( teacher.getState() != null ) {
                teacherResponse.setState( teacher.getState() );
            }
            if ( teacher.getRole() != null ) {
                teacherResponse.setRole( teacher.getRole() );
            }
        }

        return teacherResponse;
    }

    @Override
    public TeacherRegisterResponse entityToRegisterResponse(Teacher teacher) {

        TeacherRegisterResponse teacherRegisterResponse = new TeacherRegisterResponse();

        if ( teacher != null ) {
            if ( teacher.getId() != null ) {
                teacherRegisterResponse.setId( teacher.getId() );
            }
            if ( teacher.getFullName() != null ) {
                teacherRegisterResponse.setFullName( teacher.getFullName() );
            }
            if ( teacher.getUserName() != null ) {
                teacherRegisterResponse.setUserName( teacher.getUserName() );
            }
            if ( teacher.getBirthday() != null ) {
                teacherRegisterResponse.setBirthday( Date.from( teacher.getBirthday().atStartOfDay( ZoneOffset.UTC ).toInstant() ) );
            }
            if ( teacher.getPassword() != null ) {
                teacherRegisterResponse.setPassword( teacher.getPassword() );
            }
            if ( teacher.getPhone() != null ) {
                teacherRegisterResponse.setPhone( teacher.getPhone() );
            }
            if ( teacher.getAddress() != null ) {
                teacherRegisterResponse.setAddress( teacher.getAddress() );
            }
            if ( teacher.getState() != null ) {
                teacherRegisterResponse.setState( teacher.getState() );
            }
            if ( teacher.getRole() != null ) {
                teacherRegisterResponse.setRole( teacher.getRole() );
            }
        }

        return teacherRegisterResponse;
    }

    @Override
    public List<TeacherResponse> listEntityToResponse(List<Teacher> teachers) {
        if ( teachers == null ) {
            return new ArrayList<TeacherResponse>();
        }

        List<TeacherResponse> list = new ArrayList<TeacherResponse>( teachers.size() );
        for ( Teacher teacher : teachers ) {
            list.add( entityToResponse( teacher ) );
        }

        return list;
    }
}
