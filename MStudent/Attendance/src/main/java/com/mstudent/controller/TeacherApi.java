package com.mstudent.controller;

import com.mstudent.exception.NotFoundException;
import com.mstudent.model.dto.request.Teacher.UpdateTeacherRequest;
import com.mstudent.model.dto.response.Teacher.TeacherResponse;
import com.mstudent.service.TeacherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/teacher")
public class TeacherApi {

    private final TeacherService teacherService;

    public TeacherApi(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @PutMapping
    public ResponseEntity<TeacherResponse> update(@RequestBody UpdateTeacherRequest updateTeacherRequest)
        throws NotFoundException {
        TeacherResponse teacherResponse = teacherService.update(updateTeacherRequest);
        return ResponseEntity.ok(teacherResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeacherResponse> getOne(@PathVariable Long id) throws NotFoundException {
        return ResponseEntity.ok(teacherService.getById(id));
    }
}
