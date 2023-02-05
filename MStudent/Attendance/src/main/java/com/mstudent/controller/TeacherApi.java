package com.mstudent.controller;

import com.mstudent.config.JwtTokenProvider;
import com.mstudent.model.base.JwtResponse;
import com.mstudent.model.base.LoginRequest;
import com.mstudent.model.base.RefreshToken;
import com.mstudent.model.dto.request.CreateTeacherRequest;
import com.mstudent.model.dto.request.UpdateTeacherRequest;
import com.mstudent.model.entity.Teacher;
import com.mstudent.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/teacher")
public class TeacherApi {

    private final TeacherService teacherService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider jwtService;

    public TeacherApi(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping("/list")
    public List<Teacher> getAllTeacher(){
        return teacherService.getList();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest user) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtService.generateTokenLogin(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Teacher currentUser = teacherService.getByUsername(user.getUsername());
        RefreshToken refreshToken = jwtService.createRefreshToken(currentUser.getId());
        return ResponseEntity.ok(new JwtResponse(jwt, currentUser.getId(), userDetails.getUsername(), currentUser.getFullName(), userDetails.getAuthorities(), refreshToken.getToken()));
    }

    @PostMapping("/register")
    public ResponseEntity<Teacher> register(@RequestBody CreateTeacherRequest createTeacherRequest){
        Teacher teacher = teacherService.insert(createTeacherRequest);
        return ResponseEntity.ok(teacher);
    }

    @PutMapping
    public ResponseEntity<Teacher> update(@RequestBody UpdateTeacherRequest updateTeacherRequest){
        Teacher teacher = teacherService.update(updateTeacherRequest);
        return ResponseEntity.ok(teacher);
    }

    @PostMapping("/remove-permission/{id}")
    public ResponseEntity<Teacher> removePermission(@PathVariable Long id){
        return ResponseEntity.ok(teacherService.stopTeacher(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Teacher> getOne(@PathVariable Long id){
        return ResponseEntity.ok(teacherService.getById(id));
    }
}
