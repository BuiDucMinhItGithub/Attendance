package com.mstudent.service;

import com.mstudent.model.dto.request.CreateAttendanceRequest;
import com.mstudent.model.dto.request.StudentAttendance;
import com.mstudent.model.entity.Attendance;
import com.mstudent.repository.AttendanceRepository;
import com.mstudent.repository.RoomRepository;
import com.mstudent.repository.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final StudentRepository studentRepository;
    private final RoomRepository roomRepository;

    public AttendanceService(AttendanceRepository attendanceRepository, StudentRepository studentRepository, RoomRepository roomRepository) {
        this.attendanceRepository = attendanceRepository;
        this.studentRepository = studentRepository;
        this.roomRepository = roomRepository;
    }

    public List<Attendance> insert(CreateAttendanceRequest createAttendanceRequest){
        List<Attendance> attendances = mapRequestToEntity(createAttendanceRequest);
        return attendanceRepository.saveAll(attendances);
    }


    public List<Attendance> mapRequestToEntity(CreateAttendanceRequest createAttendanceRequest){
        List<StudentAttendance> studentAttendances = createAttendanceRequest.getStudentAttendances();
        List<Attendance> attendances = new ArrayList<>();
        studentAttendances.forEach(studentAttendance -> {
            Attendance attendance = new Attendance();
            attendance.setStudent(studentRepository.findById(studentAttendance.getId()).get());
            attendance.setRoom(roomRepository.findById(createAttendanceRequest.getRoomId()).get());
            attendance.setDate(createAttendanceRequest.getDate());
            attendances.add(attendance);
        });
        return attendances;
    }
}
