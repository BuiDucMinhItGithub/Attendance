package com.mstudent.service;

import com.mstudent.enums.StudentState;
import com.mstudent.exception.NotFoundException;
import com.mstudent.mapper.StudentMapper;
import com.mstudent.model.dto.request.Student.CreateStudentRequest;
import com.mstudent.model.dto.request.Student.UpdateStudentRequest;
import com.mstudent.model.dto.response.Student.StudentResponse;
import com.mstudent.model.dto.response.Student.StudentFullResponse;
import com.mstudent.model.dto.response.Student.StudentShortResponse;
import com.mstudent.model.entity.Student;
import com.mstudent.repository.StudentRepository;
import java.util.Objects;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
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

    public StudentResponse insert(CreateStudentRequest createStudentRequest){
        Student student = studentMapper.createRequestToEntity(createStudentRequest);
        student.setState(StudentState.ACTIVE.getValue());
        studentRepository.save(student);
        return studentMapper.entityToResponseUpdateAndInsert(student);
    }

    public StudentResponse update(UpdateStudentRequest updateStudentRequest) throws NotFoundException {
        Student student = studentRepository.findById(updateStudentRequest.getId()).get();
        if(Objects.isNull(student)){
            throw new NotFoundException("exception.notfound");
        }
        Student studentUpdate = studentMapper.updateRequestToEntity(updateStudentRequest);
        if(!StringUtils.hasText(studentUpdate.getState())){
            studentUpdate.setState(student.getState());
        }
        studentRepository.save(studentUpdate);
        return studentMapper.entityToResponseUpdateAndInsert(studentUpdate);
    }

    public StudentFullResponse getById(Long id) throws NotFoundException {
        Student student = studentRepository.findById(id).get();
        if(Objects.isNull(student)){
            throw new NotFoundException("exception.notfound");
        }
        return studentMapper.entityToResponse(student);
    }

    public List<StudentFullResponse> getListByRoomId(Long id) throws NotFoundException {
        Specification<Student> specification = hasRoomWithId(id);
        List<Student> students = studentRepository.findAll(specification);;
        if(CollectionUtils.isEmpty(students)){
            throw new NotFoundException("exception.list.null");
        }
        return studentMapper.listEntityToResponse(students);
    }


    public List<StudentShortResponse> getAllStudent() throws NotFoundException {
        List<Student> students = studentRepository.findAll();
        if(CollectionUtils.isEmpty(students)){
            throw new NotFoundException("exception.list.null");
        }
        return studentMapper.listEntityToShortResponse(students);
    }
}
