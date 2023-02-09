package com.mstudent.controller;

import com.mstudent.config.JwtTokenProvider;
import com.mstudent.exception.NotFoundException;
import com.mstudent.model.base.JwtResponse;
import com.mstudent.model.base.LoginRequest;
import com.mstudent.model.base.RefreshToken;
import com.mstudent.model.dto.request.Teacher.CreateTeacherRequest;
import com.mstudent.model.dto.request.Teacher.UpdateTeacherRequest;
import com.mstudent.model.dto.response.Teacher.TeacherResponse;
import com.mstudent.service.TeacherService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public List<TeacherResponse> getAllTeacher() throws NotFoundException {
        return teacherService.getList();
    }

//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody LoginRequest user) throws NotFoundException {
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        String jwt = jwtService.generateTokenLogin(authentication);
//        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//        TeacherResponse currentUser = teacherService.getByUsername(user.getUsername());
//        RefreshToken refreshToken = jwtService.createRefreshToken(currentUser.getId());
//        return ResponseEntity.ok(new JwtResponse(jwt, currentUser.getId(), userDetails.getUsername(),
//            currentUser.getFullName(), userDetails.getAuthorities(), refreshToken.getToken()));
//    }
//
//    @PostMapping("/register")
//    public ResponseEntity<TeacherResponse> register(@RequestBody CreateTeacherRequest createTeacherRequest){
//        TeacherResponse teacherResponse = teacherService.insert(createTeacherRequest);
//        return ResponseEntity.ok(teacherResponse);
//    }

    @PutMapping
    public ResponseEntity<TeacherResponse> update(@RequestBody UpdateTeacherRequest updateTeacherRequest)
        throws NotFoundException {
        TeacherResponse teacherResponse = teacherService.update(updateTeacherRequest);
        return ResponseEntity.ok(teacherResponse);
    }

    @PostMapping("/remove-permission/{id}")
    public ResponseEntity<TeacherResponse> removePermission(@PathVariable Long id)
        throws NotFoundException {
        return ResponseEntity.ok(teacherService.stopTeacher(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeacherResponse> getOne(@PathVariable Long id) throws NotFoundException {
        return ResponseEntity.ok(teacherService.getById(id));
    }
}
