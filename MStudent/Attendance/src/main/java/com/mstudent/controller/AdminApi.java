package com.mstudent.controller;

import com.mstudent.exception.NotFoundException;
import com.mstudent.model.dto.response.Room.RoomShortResponse;
import com.mstudent.model.dto.response.Student.StudentShortResponse;
import com.mstudent.model.dto.response.Teacher.TeacherResponse;
import com.mstudent.service.RoomService;
import com.mstudent.service.StudentService;
import com.mstudent.service.TeacherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminApi {

    private final TeacherService teacherService;

    private final RoomService roomService;

    private final StudentService studentService;

    public AdminApi(TeacherService teacherService, RoomService roomService, StudentService studentService) {
        this.teacherService = teacherService;
        this.roomService = roomService;
        this.studentService = studentService;
    }

    @GetMapping("/list-teacher")
    public List<TeacherResponse> getAllTeacher() throws NotFoundException {
        return teacherService.getList();
    }

    @PostMapping("/remove-teacher/{id}")
    public ResponseEntity<TeacherResponse> removePermission(@PathVariable Long id)
            throws NotFoundException {
        return ResponseEntity.ok(teacherService.stopTeacher(id));
    }

    @PostMapping("/list-room")
    public ResponseEntity<List<RoomShortResponse>> getAllRoom() throws NotFoundException {
        return ResponseEntity.ok(roomService.getAll());
    }

    @PostMapping("/list-student")
    public ResponseEntity<List<StudentShortResponse>> getAllStudent() throws NotFoundException {
        return ResponseEntity.ok(studentService.getAllStudent());
    }
}
