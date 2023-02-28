package com.mstudent;

import com.mstudent.enums.AttendanceState;
import com.mstudent.enums.RoleType;
import com.mstudent.enums.RoomState;
import com.mstudent.enums.StudentState;
import com.mstudent.enums.TeacherState;
import com.mstudent.exception.NotFoundException;
import com.mstudent.model.entity.Attendance;
import com.mstudent.model.entity.Room;
import com.mstudent.model.entity.Student;
import com.mstudent.model.entity.Teacher;
import com.mstudent.repository.AttendanceRepository;
import com.mstudent.repository.RoomRepository;
import com.mstudent.repository.StudentRepository;
import com.mstudent.repository.TeacherRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.reactive.function.client.ExchangeFilterFunctions;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class Main {

    @Bean
    public WebClient getWebClient(){
        return WebClient.builder()
            .baseUrl("http://localhost:8082/api/v1/cost")
            .defaultCookie("cookie-name", "cookie-value")
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/*").allowedOrigins("http://localhost:8080");
            }
        };
    }


    public static void main(String[] args) {
        DateTimeFormatter f = DateTimeFormatter.ofPattern( "dd/MM/yyyy" );
        ApplicationContext context = SpringApplication.run(Main.class, args);
        RoomRepository roomRepository = context.getBean(RoomRepository.class);
        TeacherRepository teacherRepository = context.getBean(TeacherRepository.class);
        StudentRepository studentRepository = context.getBean(StudentRepository.class);
        AttendanceRepository attendanceRepository = context.getBean(AttendanceRepository.class);

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        if(teacherRepository.findAll().size() < 1){
            Teacher teacher = new Teacher(null, "bot","bot", LocalDate.now(),passwordEncoder.encode("bot"),
                "0364463267","Ha Noi city",TeacherState.BOT.getValue(),null, RoleType.ADMIN.getValue());
            Teacher teacherMock = new Teacher(null, "Teacher Mock","mock",LocalDate.now(),passwordEncoder.encode("mock"),
                    "0367770456","Ha Noi city",TeacherState.ACTIVE.getValue(),null, RoleType.TEACHER.getValue());
            List<Teacher> teachers = new ArrayList<>();
            teachers.add(teacher);
            teachers.add(teacherMock);
            teacherRepository.saveAll(teachers);

            Student student = new Student(null, "Bot", LocalDate.now(),null, StudentState.ACTIVE.getValue(),null);
            studentRepository.save(student);
            Student studentMock = new Student(null, "Student Mock", LocalDate.now(),null, StudentState.ACTIVE.getValue(),null);
            Student studentMock1 = new Student(null, "Student Mock1", LocalDate.now(),null, StudentState.ACTIVE.getValue(),null);
            Student studentMock2 = new Student(null, "Student Mock2", LocalDate.now(),null, StudentState.ACTIVE.getValue(),null);
            Student studentMock3 = new Student(null, "Student Mock3", LocalDate.now(),null, StudentState.ACTIVE.getValue(),null);
            Student studentMock4 = new Student(null, "Student Mock4", LocalDate.now(),null, StudentState.ACTIVE.getValue(),null);
            List<Student> students = new ArrayList<>();
            students.add(studentMock);
            students.add(studentMock1);
            students.add(studentMock2);
            students.add(studentMock3);
            students.add(studentMock4);
            studentRepository.saveAll(students);

            Room room1 = new Room(null,"room1", LocalDate.now(),LocalDate.now(),
                teacher, 0, RoomState.OPEN.getValue(), BigDecimal.valueOf(70000),List.of(student), null);
            Room roomMock = new Room(null,"Math12", LocalDate.now(),LocalDate.now(),
                    teacherMock, 0, RoomState.OPEN.getValue(), BigDecimal.valueOf(70000),students, null);
            Room roomMock1 = new Room(null,"Math12 plus", LocalDate.now(),LocalDate.now(),
                    teacherMock, 0, RoomState.OPEN.getValue(), BigDecimal.valueOf(80000),students, null);
            List<Room> rooms = new ArrayList<>();
            rooms.add(room1);
            rooms.add(roomMock);
            rooms.add(roomMock1);
            roomRepository.saveAll(rooms);

//            Mock attendance for student mock
            Attendance attendance = new Attendance(null,LocalDate.parse( "11/02/2023" , f ),
                    "02-2023",  AttendanceState.PRESENT.getValue(), BigDecimal.valueOf(70000),studentMock,roomMock);
            Attendance attendance2 = new Attendance(null,LocalDate.parse( "11/02/2023" , f ),
                    "02-2023",  AttendanceState.PRESENT.getValue(), BigDecimal.valueOf(70000),studentMock1,roomMock);
            Attendance attendance3 = new Attendance(null,LocalDate.parse( "11/02/2023" , f ),
                    "02-2023",  AttendanceState.PRESENT.getValue(), BigDecimal.valueOf(70000),studentMock2,roomMock);
            Attendance attendance4 = new Attendance(null,LocalDate.parse( "11/02/2023" , f ),
                    "02-2023",  AttendanceState.PRESENT.getValue(), BigDecimal.valueOf(70000),studentMock3,roomMock);
            Attendance attendance5 = new Attendance(null,LocalDate.parse( "11/02/2023" , f ),
                    "02-2023",  AttendanceState.PRESENT.getValue(), BigDecimal.valueOf(70000),studentMock4,roomMock);

            //Mock attendance for student mock
            Attendance attendance6 = new Attendance(null,LocalDate.parse( "12/02/2023" , f ),
                    "02-2023",  AttendanceState.PRESENT.getValue(), BigDecimal.valueOf(70000),studentMock,roomMock);
            Attendance attendance7 = new Attendance(null,LocalDate.parse( "12/02/2023" , f ),
                    "02-2023",  AttendanceState.PRESENT.getValue(), BigDecimal.valueOf(70000),studentMock1,roomMock);
            Attendance attendance8 = new Attendance(null,LocalDate.parse( "12/02/2023" , f ),
                    "02-2023",  AttendanceState.PRESENT.getValue(), BigDecimal.valueOf(70000),studentMock2,roomMock);
            Attendance attendance9 = new Attendance(null,LocalDate.parse( "12/02/2023" , f ),
                    "02-2023",  AttendanceState.PRESENT.getValue(), BigDecimal.valueOf(70000),studentMock3,roomMock);
            Attendance attendance10 = new Attendance(null,LocalDate.parse( "12/02/2023" , f ),
                    "02-2023",  AttendanceState.PRESENT.getValue(), BigDecimal.valueOf(70000),studentMock4,roomMock);

            //Mock attendance for student mock
            Attendance attendance11 = new Attendance(null,LocalDate.parse( "13/02/2023" , f ),
                    "02-2023",  AttendanceState.PRESENT.getValue(), BigDecimal.valueOf(70000),studentMock,roomMock);
            Attendance attendance12 = new Attendance(null,LocalDate.parse( "13/02/2023" , f ),
                    "02-2023",  AttendanceState.PRESENT.getValue(), BigDecimal.valueOf(70000),studentMock1,roomMock);
            Attendance attendance13 = new Attendance(null,LocalDate.parse( "13/02/2023" , f ),
                    "02-2023",  AttendanceState.PRESENT.getValue(), BigDecimal.valueOf(70000),studentMock2,roomMock);
            Attendance attendance14 = new Attendance(null,LocalDate.parse( "13/02/2023" , f ),
                    "02-2023",  AttendanceState.PRESENT.getValue(), BigDecimal.valueOf(70000),studentMock3,roomMock);
            Attendance attendance15 = new Attendance(null,LocalDate.parse( "13/02/2023" , f ),
                    "02-2023",  AttendanceState.PRESENT.getValue(), BigDecimal.valueOf(70000),studentMock4,roomMock);


            List<Attendance> attendances = new ArrayList<>();
            attendances.add(attendance);
            attendances.add(attendance2);
            attendances.add(attendance3);
            attendances.add(attendance4);
            attendances.add(attendance5);
            attendances.add(attendance6);
            attendances.add(attendance7);
            attendances.add(attendance8);
            attendances.add(attendance9);
            attendances.add(attendance10);
            attendances.add(attendance11);
            attendances.add(attendance12);
            attendances.add(attendance13);
            attendances.add(attendance14);
            attendances.add(attendance15);
            attendanceRepository.saveAll(attendances);

        }

//        SpringApplication.run(Main.class, args);
    }


}