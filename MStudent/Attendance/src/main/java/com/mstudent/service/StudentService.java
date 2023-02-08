package com.mstudent.service;

import com.mstudent.enums.StudentState;
import com.mstudent.mapper.StudentMapper;
import com.mstudent.model.dto.request.Student.CreateStudentRequest;
import com.mstudent.model.dto.request.Student.UpdateStudentRequest;
import com.mstudent.model.entity.Student;
import com.mstudent.repository.StudentRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.mstudent.specification.StudentSpecification.hasRoomWithId;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    public StudentService(StudentRepository studentRepository, StudentMapper studentMapper) {
        this.studentRepository = studentRepository;
        this.studentMapper = studentMapper;
    }

    public Student insert(CreateStudentRequest createStudentRequest){
        Student student = studentMapper.createRequestToEntity(createStudentRequest);
        student.setState(StudentState.ACTIVE.getValue());
        return studentRepository.save(student);
    }

    public Student update(UpdateStudentRequest updateStudentRequest){
        Student student = studentRepository.findById(updateStudentRequest.getId()).get();
        Student studentUpdate = studentMapper.updateRequestToEntity(updateStudentRequest);
        if(!StringUtils.hasText(studentUpdate.getState())){
            studentUpdate.setState(student.getState());
        }
        return studentRepository.save(studentUpdate);
    }

    public Student getById(Long id){
        return studentRepository.findById(id).get();
    }

    public List<Student> getListByRoomId(Long id){
        Specification<Student> specification = hasRoomWithId(id);
        return studentRepository.findAll(specification);
    }
}
