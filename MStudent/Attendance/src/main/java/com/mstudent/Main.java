package com.mstudent;

import com.mstudent.enums.RoleType;
import com.mstudent.enums.RoomState;
import com.mstudent.enums.StudentState;
import com.mstudent.enums.TeacherState;
import com.mstudent.model.entity.Room;
import com.mstudent.model.entity.Student;
import com.mstudent.model.entity.Teacher;
import com.mstudent.repository.RoomRepository;
import com.mstudent.repository.StudentRepository;
import com.mstudent.repository.TeacherRepository;
import java.math.BigDecimal;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Main.class, args);
        RoomRepository roomRepository = context.getBean(RoomRepository.class);
        TeacherRepository teacherRepository = context.getBean(TeacherRepository.class);
        StudentRepository studentRepository = context.getBean(StudentRepository.class);

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        if(teacherRepository.findAll().size() < 1){
            Teacher teacher = new Teacher(null, "bot","bot",Date.from(OffsetDateTime.now().toInstant()),passwordEncoder.encode("bot"),
                "0364463267","Ha Noi city",TeacherState.BOT.getValue(),null, RoleType.ADMIN.getValue());
            teacherRepository.save(teacher);

            Student student = new Student(null, "Bot", Date.from(OffsetDateTime.now().toInstant()),null, StudentState.ACTIVE.getValue(),null);
            studentRepository.save(student);

            List<Student> students = new ArrayList<>();
            students.add(student);

            Room room1 = new Room(null,"room1", Date.from(OffsetDateTime.now().toInstant()),Date.from(OffsetDateTime.now().toInstant()),
                teacher, 0, RoomState.OPEN.getValue(), BigDecimal.valueOf(70000),students, null);
            List<Room> rooms = new ArrayList<>();
            rooms.add(room1);
            roomRepository.saveAll(rooms);
        }

//        SpringApplication.run(Main.class, args);
    }
}