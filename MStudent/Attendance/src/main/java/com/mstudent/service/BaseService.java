package com.mstudent.service;

import com.mstudent.exception.NotFoundException;
import com.mstudent.model.entity.Teacher;
import com.mstudent.repository.RoomRepository;
import com.mstudent.repository.TeacherRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BaseService {

    protected Teacher getTeacher() throws NotFoundException {
        org.springframework.security.core.Authentication auth =
                SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            throw new NotFoundException("exception.notfound");
        }
        return (Teacher) auth.getPrincipal();
    }
}
