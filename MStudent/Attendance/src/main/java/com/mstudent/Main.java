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
//        ApplicationContext context = SpringApplication.run(Main.class, args);
//        RoomRepository roomRepository = context.getBean(RoomRepository.class);
//        TeacherRepository teacherRepository = context.getBean(TeacherRepository.class);
//        StudentRepository studentRepository = context.getBean(StudentRepository.class);
//
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//
//        Teacher teacher = new Teacher(null, "bot","bot",passwordEncoder.encode("bot"),
//            TeacherState.BOT.getValue(),null, RoleType.ADMIN.getValue());
//        teacherRepository.save(teacher);
//
//        Student student = new Student(null, "Bot", Date.from(OffsetDateTime.now().toInstant()),null, StudentState.BOT.getValue());
//        studentRepository.save(student);
//
//        List<Student> students = new ArrayList<>();
//        students.add(student);
//
//        Room room1 = new Room(null,"room1", Date.from(OffsetDateTime.now().toInstant()),Date.from(OffsetDateTime.now().toInstant()),
//                teacher, 0, RoomState.OPEN.getValue(),students);
//        Room room2 = new Room(null,"room2", Date.from(OffsetDateTime.now().toInstant()),Date.from(OffsetDateTime.now().toInstant()),
//                teacher, 0, RoomState.OPEN.getValue(),students);
//        Room room3 = new Room(null,"room3", Date.from(OffsetDateTime.now().toInstant()),Date.from(OffsetDateTime.now().toInstant()),
//                teacher, 0, RoomState.OPEN.getValue(),students);
//        List<Room> rooms = new ArrayList<>();
//        rooms.add(room1);
//        rooms.add(room2);
//        rooms.add(room3);
//        roomRepository.saveAll(rooms);

        SpringApplication.run(Main.class, args);
    }
}