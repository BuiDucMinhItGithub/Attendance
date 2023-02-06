package com.mstudent.service;

import com.mstudent.exception.NotFoundException;
import com.mstudent.model.dto.request.CreateAttendanceRequest;
import com.mstudent.model.dto.request.StudentAttendance;
import com.mstudent.model.dto.request.UpdateAttendanceRequest;
import com.mstudent.model.entity.Attendance;
import com.mstudent.repository.AttendanceRepository;
import com.mstudent.repository.RoomRepository;
import com.mstudent.repository.StudentRepository;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.util.CollectionUtils;

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

    public Attendance getById(Long id) throws NotFoundException {
        Optional<Attendance> attendanceOptional = attendanceRepository.findById(id);
        if(!attendanceOptional.isPresent()){
            throw new NotFoundException("Khong ton tai");
        }
        return attendanceOptional.get();
    }

    public List<Attendance> getListByRoomAndDate(Long roomId, Date date) throws NotFoundException {
        List<Attendance> attendances = attendanceRepository.findAllByRoomIdAndDate(roomId, date);
        if(CollectionUtils.isEmpty(attendances)){
            throw new NotFoundException("Khong ton tai");
        }
        return attendances;
    }

    public List<Attendance> getAll() throws NotFoundException {
        List<Attendance> attendances = attendanceRepository.findAll();
        if(CollectionUtils.isEmpty(attendances)){
            throw new NotFoundException("Khong ton tai");
        }
        return attendances;
    }

    public List<Attendance> update(UpdateAttendanceRequest updateAttendanceRequest){
        List<Attendance> attendances = processUpdateRequest(updateAttendanceRequest);
        return attendanceRepository.saveAll(attendances);
    }

    public List<Attendance> getListFilterByDate(Date fromDate, Date toDate, Long roomId){
        return attendanceRepository.findByRoomAndDate(fromDate, toDate, roomId);
    }

    public List<Attendance> getListToProcessPrice(Date fromDate, Date toDate, Long roomId, Long studentId){
        return attendanceRepository.findAllByRoomAndStudentIdWithFromToDate(fromDate, toDate, roomId,studentId);
    }


    public List<Attendance> mapRequestToEntity(CreateAttendanceRequest createAttendanceRequest){
        List<StudentAttendance> studentAttendances = createAttendanceRequest.getStudentAttendances();
        List<Attendance> attendances = new ArrayList<>();
        studentAttendances.forEach(studentAttendance -> {
            Attendance attendance = new Attendance();
            attendance.setStudent(studentRepository.findById(studentAttendance.getId()).get());
            attendance.setRoom(roomRepository.findById(createAttendanceRequest.getRoomId()).get());
            attendance.setDate(createAttendanceRequest.getDate());
            attendance.setState(studentAttendance.getState());
            attendances.add(attendance);
        });
        return attendances;
    }

    public List<Attendance> processUpdateRequest(UpdateAttendanceRequest updateAttendanceRequest){
        List<StudentAttendance> studentAttendances = updateAttendanceRequest.getStudentAttendances();
        studentAttendances.forEach(studentAttendance -> {
            // lay thong tin diem danh tu date, roomid, studentid va state
            Attendance attendance = attendanceRepository.findByRoomAndStudentIdWithDateAndState(
                updateAttendanceRequest.getRoomId(), studentAttendance.getId(),
                updateAttendanceRequest.getDate(), studentAttendance.getState());
            // Neu khong tim thay thi tao moi 1 thong tin diem danh cua sinh vien do
            if(Objects.isNull(attendance)){
                Attendance attendanceToRemove = attendanceRepository.findByRoomAndStudentIdWithDate(
                    updateAttendanceRequest.getRoomId(), studentAttendance.getId(),
                    updateAttendanceRequest.getDate());
                attendanceRepository.delete(attendanceToRemove);
                // Sau khi xoa thong tin diem danh cu se them moi
                Attendance attendanceUpdate = new Attendance();
                attendanceUpdate.setStudent(studentRepository.findById(studentAttendance.getId()).get());
                attendanceUpdate.setRoom(roomRepository.findById(updateAttendanceRequest.getRoomId()).get());
                attendanceUpdate.setDate(updateAttendanceRequest.getDate());
                attendanceUpdate.setState(studentAttendance.getState());
                attendanceRepository.save(attendanceUpdate);
                // Gui kafka tai day
            }
        });
        return attendanceRepository.findAllByRoomIdAndDate(updateAttendanceRequest.getRoomId(),
            updateAttendanceRequest.getDate());
    }
}
