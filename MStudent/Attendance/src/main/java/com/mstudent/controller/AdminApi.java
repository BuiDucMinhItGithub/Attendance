package com.mstudent.controller;

import com.mstudent.exception.NotFoundException;
import com.mstudent.model.dto.response.Teacher.TeacherResponse;
import com.mstudent.service.TeacherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminApi {

    private final TeacherService teacherService;

    public AdminApi(TeacherService teacherService) {
        this.teacherService = teacherService;
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
}
