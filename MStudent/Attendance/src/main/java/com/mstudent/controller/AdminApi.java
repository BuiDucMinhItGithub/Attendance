package com.mstudent.controller;

import com.mstudent.exception.NotFoundException;
import com.mstudent.model.dto.response.Room.RoomCreateResponse;
import com.mstudent.model.dto.response.Room.RoomResponse;
import com.mstudent.model.dto.response.Room.RoomShortResponse;
import com.mstudent.model.dto.response.Teacher.TeacherResponse;
import com.mstudent.service.RoomService;
import com.mstudent.service.TeacherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminApi {

    private final TeacherService teacherService;

    private final RoomService roomService;

    public AdminApi(TeacherService teacherService, RoomService roomService) {
        this.teacherService = teacherService;
        this.roomService = roomService;
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
    public ResponseEntity<List<RoomShortResponse>> getAllRooms() throws NotFoundException {
        return ResponseEntity.ok(roomService.getAll());
    }
}
