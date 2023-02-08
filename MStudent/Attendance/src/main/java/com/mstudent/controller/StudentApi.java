package com.mstudent.controller;

import com.mstudent.model.dto.request.Student.CreateStudentRequest;
import com.mstudent.model.dto.request.Student.UpdateStudentRequest;
import com.mstudent.model.entity.Student;
import com.mstudent.service.StudentService;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/student")
public class StudentApi {

  private final StudentService studentService;

  public StudentApi(StudentService studentService) {
    this.studentService = studentService;
  }

  @PostMapping
  public ResponseEntity<Student> insert(@RequestBody CreateStudentRequest createStudentRequest){
    return ResponseEntity.ok(studentService.insert(createStudentRequest));
  }
  @PutMapping
  public ResponseEntity<Student> update(@RequestBody UpdateStudentRequest updateStudentRequest){
    return ResponseEntity.ok(studentService.update(updateStudentRequest));
  }
  @GetMapping("/{id}")
  public ResponseEntity<Student> getOne(@PathVariable Long id){
    return ResponseEntity.ok(studentService.getById(id));
  }

  @GetMapping("/student-room/{roomId}")
  public ResponseEntity<List<Student>> getListByRoom(@PathVariable Long roomId){
    return ResponseEntity.ok(studentService.getListByRoomId(roomId));
  }
}
