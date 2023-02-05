package com.mstudent.service;

import com.mstudent.enums.TeacherState;
import com.mstudent.model.base.CustomUserDetails;
import com.mstudent.model.entity.Teacher;
import com.mstudent.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    TeacherRepository teacherRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Teacher teacher = teacherRepository.findByUserNameAndStateIs(username, TeacherState.ACTIVE.getValue());
        if (teacher == null) {
            throw new UsernameNotFoundException(username);
        }
        return new CustomUserDetails(teacher);
    }

    public UserDetails loadUserById(Long id) throws UsernameNotFoundException {
        Optional<Teacher> teacher = teacherRepository.findById(id);
        if (teacher.isPresent()) {
            throw new UsernameNotFoundException(""+id);
        }
        return new CustomUserDetails(teacher.get());
    }
}
