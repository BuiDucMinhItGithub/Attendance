package com.mstudent.service;

import com.mstudent.enums.RoleType;
import com.mstudent.enums.TeacherState;
import com.mstudent.exception.NotFoundException;
import com.mstudent.mapper.TeacherMapper;
import com.mstudent.model.dto.request.Teacher.CreateTeacherRequest;
import com.mstudent.model.dto.request.Teacher.UpdateTeacherRequest;
import com.mstudent.model.dto.response.Teacher.TeacherResponse;
import com.mstudent.model.entity.Teacher;
import com.mstudent.repository.TeacherRepository;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class TeacherService {

    private final TeacherRepository teacherRepository;
    private final TeacherMapper teacherMapper;

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public TeacherService(TeacherRepository teacherRepository, TeacherMapper teacherMapper) {
        this.teacherRepository = teacherRepository;
        this.teacherMapper = teacherMapper;
    }

    public TeacherResponse insert(CreateTeacherRequest createTeacherRequest){
        Teacher teacher = teacherMapper.createRequestToEntity(createTeacherRequest);
        teacher.setPassword(passwordEncoder.encode(teacher.getPassword()));
        teacher.setRooms(null);
        teacher.setRole(RoleType.TEACHER.getValue());
        teacher.setState(TeacherState.ACTIVE.getValue());
        teacherRepository.save(teacher);
        return teacherMapper.entityToResponse(teacher);
    }

    public TeacherResponse update(UpdateTeacherRequest updateTeacherRequest)
        throws NotFoundException {
        Teacher teacher = teacherRepository.findById(updateTeacherRequest.getId()).get();
        if(Objects.isNull(teacher)){
            throw new NotFoundException("exception.notfound");
        }
        Teacher teacherUpdate = teacherMapper.updateRequestToEntity(updateTeacherRequest);
        if(!StringUtils.hasText(updateTeacherRequest.getState())){
            teacherUpdate.setState(teacher.getState());
        }
        teacherUpdate.setRole(teacher.getRole());
        teacherRepository.save(teacherUpdate);
       return teacherMapper.entityToResponse(teacherUpdate);
    }

    public TeacherResponse stopTeacher(Long id) throws NotFoundException {
        Teacher teacher = teacherRepository.findById(id).get();
        if(Objects.isNull(teacher)){
            throw new NotFoundException("exception.notfound");
        }
        teacher.setState(TeacherState.DEACTIVE.getValue());
        teacherRepository.save(teacher);
        return teacherMapper.entityToResponse(teacher);
    }

    public List<TeacherResponse> getList() throws NotFoundException {
        List<Teacher> teachers = teacherRepository.findAll();
        if(CollectionUtils.isEmpty(teachers)){
            throw new NotFoundException("exception.list.null");
        }
        return teacherMapper.listEntityToResponse(teachers);
    }

    public TeacherResponse getByUsername(String name) throws NotFoundException {
        Teacher teacher = teacherRepository.findByUserNameAndStateIs(name, TeacherState.ACTIVE.getValue());
        if(Objects.isNull(teacher)){
            throw new NotFoundException("exception.notfound");
        }
        return teacherMapper.entityToResponse(teacher);
    }

    public TeacherResponse getById(Long id) throws NotFoundException {
        Optional<Teacher> teacherOptional = teacherRepository.findById(id);
        if(!teacherOptional.isPresent()){
            throw new NotFoundException("exception.notfound");
        }
        return teacherMapper.entityToResponse(teacherOptional.get());
    }

}
