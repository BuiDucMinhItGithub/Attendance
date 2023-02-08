package com.mstudent.service;

import com.mstudent.enums.AttendanceState;
import com.mstudent.enums.CostState;
import com.mstudent.exception.NotFoundException;
import com.mstudent.model.dto.request.Attendance.CreateAttendanceRequest;
import com.mstudent.model.dto.request.Attendance.StudentAttendance;
import com.mstudent.model.dto.request.Attendance.UpdateAttendanceRequest;
import com.mstudent.model.entity.Attendance;
import com.mstudent.model.entity.Cost;
import com.mstudent.model.entity.Room;
import com.mstudent.model.entity.Student;
import com.mstudent.repository.AttendanceRepository;
import com.mstudent.repository.CostRepository;
import com.mstudent.repository.RoomRepository;
import com.mstudent.repository.StudentRepository;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
@Slf4j
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final StudentRepository studentRepository;
    private final RoomRepository roomRepository;
    private final CostRepository costRepository;

    public AttendanceService(AttendanceRepository attendanceRepository, StudentRepository studentRepository, RoomRepository roomRepository,
        CostRepository costRepository) {
        this.attendanceRepository = attendanceRepository;
        this.studentRepository = studentRepository;
        this.roomRepository = roomRepository;
        this.costRepository = costRepository;
    }

    public List<Attendance> insert(CreateAttendanceRequest createAttendanceRequest){
        List<Attendance> attendances = mapRequestToEntity(createAttendanceRequest);
        return attendanceRepository.saveAll(attendances);
    }

    public Attendance getById(Long id) throws NotFoundException {
        log.info("Start retrieve attendance with id = %s", id);
        Optional<Attendance> attendanceOptional = attendanceRepository.findById(id);
        if(!attendanceOptional.isPresent()){
            throw new NotFoundException("exception.notfound");
        }
        return attendanceOptional.get();
    }

    public List<Attendance> getListByRoomAndDate(Long roomId, Date date) throws NotFoundException {
        log.info("Start retrieve attendance with room-id = %x and date = %y", roomId, date);
        List<Attendance> attendances = attendanceRepository.findAllByRoomIdAndDate(roomId, date);
        if(CollectionUtils.isEmpty(attendances)){
            throw new NotFoundException("exception.list.null");
        }
        return attendances;
    }

    public List<Attendance> getAll() throws NotFoundException {
        log.info("Start retrieve all attendances");
        List<Attendance> attendances = attendanceRepository.findAll();
        if(CollectionUtils.isEmpty(attendances)){
            throw new NotFoundException("exception.list.null");
        }
        return attendances;
    }

    public List<Attendance> update(UpdateAttendanceRequest updateAttendanceRequest){
        log.info("Start update attendance at date = %s",updateAttendanceRequest.getDate());
        List<Attendance> attendances = processUpdateRequest(updateAttendanceRequest);
        return attendanceRepository.saveAll(attendances);
    }

    public List<Attendance> getListFilterByDate(Date fromDate, Date toDate, Long roomId)
        throws NotFoundException {
        List<Attendance> attendances =  attendanceRepository.findByRoomAndRangeDate(fromDate, toDate, roomId);
        if(CollectionUtils.isEmpty(attendances)){
            throw new NotFoundException("exception.list.null");
        }
        return attendances;
    }

    public List<Attendance> getListToProcessPrice(Date fromDate, Date toDate, Long roomId, Long studentId)
        throws NotFoundException {
        List<Attendance> attendances =  attendanceRepository.findAllByRoomAndStudentIdWithFromToDate(fromDate, toDate, roomId,studentId);
        if(CollectionUtils.isEmpty(attendances)){
            throw new NotFoundException("exception.list.null");
        }
        return attendances;
    }


    public List<Attendance> mapRequestToEntity(CreateAttendanceRequest createAttendanceRequest){
        List<StudentAttendance> studentAttendances = createAttendanceRequest.getStudentAttendances();
        List<Attendance> attendances = new ArrayList<>();
        studentAttendances.forEach(studentAttendance -> {
            Attendance attendance = new Attendance();
            attendance.setStudent(studentRepository.findById(studentAttendance.getId()).get());
            attendance.setRoom(roomRepository.findById(createAttendanceRequest.getRoomId()).get());
            attendance.setDate(createAttendanceRequest.getDate());
            attendance.setMonth(getMonthAndYear(createAttendanceRequest.getDate()));
            attendance.setState(studentAttendance.getState());
            attendances.add(attendance);
        });
        return attendances;
    }

    public List<Attendance> processUpdateRequest(UpdateAttendanceRequest updateAttendanceRequest){
        List<StudentAttendance> studentAttendances = updateAttendanceRequest.getStudentAttendances();
        studentAttendances.forEach(studentAttendance -> {
            log.info("Get attendance of student at lesson need to update");
            Attendance attendance = attendanceRepository.findByRoomAndStudentIdWithDateAndState(
                updateAttendanceRequest.getRoomId(), studentAttendance.getId(),
                updateAttendanceRequest.getDate(), studentAttendance.getState());
            // Neu khong tim thay thi tao moi 1 thong tin diem danh cua sinh vien do
            // Sinh vien khac state se khong tim thay
            if(Objects.isNull(attendance)){
                log.info("Start update attendance for student");
                Attendance attendanceToRemove = attendanceRepository.findByRoomAndStudentIdWithDate(
                    updateAttendanceRequest.getRoomId(), studentAttendance.getId(),
                    updateAttendanceRequest.getDate());
                attendanceRepository.delete(attendanceToRemove);
                log.info("Finished delete old attendance for student with id = %s", attendanceToRemove.getStudent().getId());
                // Sau khi xoa thong tin diem danh cu se them moi
                Attendance attendanceUpdate = new Attendance();
                attendanceUpdate.setStudent(studentRepository.findById(studentAttendance.getId()).get());
                attendanceUpdate.setRoom(roomRepository.findById(updateAttendanceRequest.getRoomId()).get());
                attendanceUpdate.setDate(updateAttendanceRequest.getDate());
                attendanceUpdate.setMonth(getMonthAndYear(updateAttendanceRequest.getDate()));
                attendanceUpdate.setState(studentAttendance.getState());
                log.info("Save new attendance for student with id = %s", attendanceUpdate.getStudent().getId());
                attendanceRepository.save(attendanceUpdate);
                // Gui kafka tai day
            }
        });
        return attendanceRepository.findAllByRoomIdAndDate(updateAttendanceRequest.getRoomId(),
            updateAttendanceRequest.getDate());
    }

    public boolean processPricePerMonth(Long roomId, Date date) throws NotFoundException {
        // Lay danh sach diem danh cua thang
        log.info("Start process to count price per month for student");
        log.info("Start retrieve room with id = %s",roomId);
        Room room = roomRepository.findById(roomId).get();
        if(Objects.isNull(room)){
            throw new NotFoundException("exception.notfound");
        }
        List<Student> students = room.getStudents();
        if(CollectionUtils.isEmpty(students)){
            throw new NotFoundException("exception.list.null");
        }
        List<Cost> costs = new ArrayList<>();

        students.forEach(student -> {
            log.info("Start create cost per month for student with id = %s", student.getId());
            List<Attendance> attendancesOfStudents =
                attendanceRepository.findAllByRoomAndStudentIdAndMonth( getMonthAndYear(date),
                    roomId, student.getId(), AttendanceState.PRESENT.getValue());
            Cost cost = new Cost();
            cost.setPrice(room.getPricePerLesson().subtract(
                BigDecimal.valueOf(attendancesOfStudents.size())));
            LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            int month = localDate.getMonthValue();
            cost.setMonth(month);
            cost.setStudent(student);
            cost.setNumberOfLesson(attendancesOfStudents.size());
            cost.setRoom(room);
            cost.setState(CostState.NOT_YET.getValue());
            costs.add(cost);
        });
        costRepository.saveAll(costs);
        log.info("Finished create cost for student in room with id =%s", roomId);
        if(students.size() >= costs.size()){
            return false;
        }

        return true;
    }

    public String getMonthAndYear(Date date){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
        String strDate = dateFormat.format(date);
        String[] splits = strDate.split("-");
        return splits[0].concat(splits[1]);
    }
}
