package com.mstudent.service;

import com.mstudent.enums.RoleType;
import com.mstudent.enums.TeacherState;
import com.mstudent.mapper.TeacherMapper;
import com.mstudent.model.dto.request.CreateTeacherRequest;
import com.mstudent.model.dto.request.UpdateTeacherRequest;
import com.mstudent.model.entity.Teacher;
import com.mstudent.repository.TeacherRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class TeacherService {

    private final TeacherRepository teacherRepository;
    private final TeacherMapper teacherMapper;

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public TeacherService(TeacherRepository teacherRepository, TeacherMapper teacherMapper) {
        this.teacherRepository = teacherRepository;
        this.teacherMapper = teacherMapper;
    }

    public Teacher insert(CreateTeacherRequest createTeacherRequest){
        Teacher teacher = teacherMapper.createRequestToEntity(createTeacherRequest);
        teacher.setPassword(passwordEncoder.encode(teacher.getPassword()));
        teacher.setRooms(null);
        teacher.setRole(RoleType.TEACHER.getValue());
        teacher.setState(TeacherState.ACTIVE.getValue());
        return teacherRepository.save(teacher);
    }

    public Teacher update(UpdateTeacherRequest updateTeacherRequest){
        Teacher teacher = teacherRepository.findById(updateTeacherRequest.getId()).get();
        Teacher teacherUpdate = teacherMapper.updateRequestToEntity(updateTeacherRequest);
        if(!StringUtils.hasText(updateTeacherRequest.getState())){
            teacherUpdate.setState(teacher.getState());
        }
        teacherUpdate.setRole(teacher.getRole());
       return teacherRepository.save(teacherUpdate);
    }

    public Teacher stopTeacher(Long id){
        Teacher teacher = teacherRepository.findById(id).get();
        teacher.setState(TeacherState.DEACTIVE.getValue());
        return teacherRepository.save(teacher);
    }

    public List<Teacher> getList(){
        return teacherRepository.findAll();
    }

    public Teacher getByUsername(String name){
        return teacherRepository.findByUserNameAndStateIs(name, TeacherState.ACTIVE.getValue());
    }

    public Teacher getById(Long id){
        return teacherRepository.findById(id).get();
    }

}
