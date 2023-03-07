package com.mstudent.controller;

import com.mstudent.config.JwtTokenProvider;
import com.mstudent.exception.NotFoundException;
import com.mstudent.model.base.JwtResponse;
import com.mstudent.model.base.LoginRequest;
import com.mstudent.model.base.RefreshToken;
import com.mstudent.model.dto.request.Teacher.CreateTeacherRequest;
import com.mstudent.model.dto.response.Teacher.TeacherRegisterResponse;
import com.mstudent.model.dto.response.Teacher.TeacherResponse;
import com.mstudent.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthApi {

  private final TeacherService teacherService;
  @Autowired
  private AuthenticationManager authenticationManager;
  @Autowired
  private JwtTokenProvider jwtService;

  public AuthApi(TeacherService teacherService) {
    this.teacherService = teacherService;
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody LoginRequest user) throws NotFoundException {
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = jwtService.generateTokenLogin(authentication);
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    TeacherResponse currentUser = teacherService.getByUsername(user.getUsername());
    RefreshToken refreshToken = jwtService.createRefreshToken(currentUser.getId());
    return ResponseEntity.ok(new JwtResponse(jwt, currentUser.getId(), userDetails.getUsername(),
        currentUser.getFullName(), userDetails.getAuthorities(), refreshToken.getToken()));
  }

  @PostMapping("/register")
  public ResponseEntity<TeacherRegisterResponse> register(@RequestBody CreateTeacherRequest createTeacherRequest){
    TeacherRegisterResponse teacherRegisterResponse = teacherService.insert(createTeacherRequest);
    return ResponseEntity.ok(teacherRegisterResponse);
  }

}
