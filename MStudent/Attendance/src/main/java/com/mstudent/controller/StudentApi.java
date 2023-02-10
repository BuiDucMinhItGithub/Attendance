package com.mstudent.controller;

import com.mstudent.exception.NotFoundException;
import com.mstudent.model.dto.request.Student.CreateStudentRequest;
import com.mstudent.model.dto.request.Student.UpdateStudentRequest;
import com.mstudent.model.dto.response.Student.StudentResponse;
import com.mstudent.model.dto.response.Student.StudentFullResponse;
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
  public ResponseEntity<StudentResponse> insert(@RequestBody CreateStudentRequest createStudentRequest){
    return ResponseEntity.ok(studentService.insert(createStudentRequest));
  }
  @PutMapping
  public ResponseEntity<StudentResponse> update(@RequestBody UpdateStudentRequest updateStudentRequest)
      throws NotFoundException {
    return ResponseEntity.ok(studentService.update(updateStudentRequest));
  }
  @GetMapping("/{id}")
  public ResponseEntity<StudentFullResponse> getOne(@PathVariable Long id) throws NotFoundException {
    return ResponseEntity.ok(studentService.getById(id));
  }

  @GetMapping("/student-room/{roomId}")
  public ResponseEntity<List<StudentFullResponse>> getListStudentByRoom(@PathVariable Long roomId)
      throws NotFoundException {
    return ResponseEntity.ok(studentService.getListByRoomId(roomId));
  }
}
