package com.mstudent.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mstudent.enums.AttendanceState;
import com.mstudent.enums.CostState;
import com.mstudent.enums.KafkaMessageType;
import com.mstudent.exception.NotFoundException;
import com.mstudent.mapper.AttendanceMapper;
import com.mstudent.model.dto.request.Attendance.AttendanceKafkaMessage;
import com.mstudent.model.dto.request.Attendance.CreateAttendanceRequest;
import com.mstudent.model.dto.request.Attendance.StudentAttendance;
import com.mstudent.model.dto.request.Attendance.UpdateAttendanceRequest;
import com.mstudent.model.dto.response.Attendance.AttendanceResponse;
import com.mstudent.model.entity.Attendance;
import com.mstudent.model.entity.Cost;
import com.mstudent.repository.AttendanceRepository;
import com.mstudent.repository.CostRepository;
import com.mstudent.repository.RoomRepository;
import com.mstudent.repository.StudentRepository;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.mstudent.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class AttendanceService {

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    WebClient webClient;
    private final AttendanceRepository attendanceRepository;
    private final StudentRepository studentRepository;
    private final RoomRepository roomRepository;
    private final CostRepository costRepository;
    private final AttendanceMapper attendanceMapper;
    private final DateUtils dateUtils;

    public AttendanceService(AttendanceRepository attendanceRepository, StudentRepository studentRepository, RoomRepository roomRepository,
                             CostRepository costRepository, AttendanceMapper attendanceMapper, DateUtils dateUtils) {
        this.attendanceRepository = attendanceRepository;
        this.studentRepository = studentRepository;
        this.roomRepository = roomRepository;
        this.costRepository = costRepository;
        this.attendanceMapper = attendanceMapper;
        this.dateUtils = dateUtils;
    }

    public List<AttendanceResponse> insert(CreateAttendanceRequest createAttendanceRequest)
        throws NotFoundException {
        log.info("Start save attendance for student");
        if(CollectionUtils.isEmpty(createAttendanceRequest.getStudentAttendances())){
            throw new NotFoundException("exception.list.null");
        }
        List<Attendance> attendances = mapRequestToEntity(createAttendanceRequest);
        if(CollectionUtils.isEmpty(attendances)){
            throw new NotFoundException("exception.list.null");
        }
        attendanceRepository.saveAll(attendances);
        attendances.forEach(attendance -> {
            //check cost
//            Cost costCheck = costRepository.findAllByRoomIdAndStudentIdAndMonth(createAttendanceRequest.getRoomId(), attendance.getStudent().getId(),
//                attendance.getMonth());
            Cost costCheck = null;
            try {
                costCheck = webClient.get()
                    .uri(uriBuilder -> uriBuilder.path("/room-student-month")
                        .queryParam("roomId", createAttendanceRequest.getRoomId())
                        .queryParam("studentId",attendance.getStudent().getId())
                        .queryParam("month",attendance.getMonth())
                        .build())
                        .accept(MediaType.APPLICATION_JSON)
                        .retrieve()
                        .onStatus(httpStatus -> HttpStatus.NOT_FOUND.equals(httpStatus), this::handleErrors)
                        .bodyToMono(Cost.class).onErrorReturn(new Cost()).block();
            } catch (Exception e){
                log.info(e.getMessage());
            }

            if(Objects.isNull(costCheck.getId()) && Objects.isNull(costCheck.getPrice())){
                if(attendance.getState().equals(AttendanceState.PRESENT.getValue())){
                    com.mstudent.model.dto.request.Cost.Cost cost = new com.mstudent.model.dto.request.Cost.Cost();
                    cost.setState(CostState.NOT_YET.getValue());
                    cost.setStudentId(attendance.getStudent().getId());
                    cost.setRoomId(roomRepository.findById(createAttendanceRequest.getRoomId()).get().getId());
                    cost.setPrice(attendance.getPrice());
                    cost.setMonth(attendance.getMonth());
                    webClient.post()
                        .uri(uriBuilder -> uriBuilder.build())
                        .body(Mono.just(cost), com.mstudent.model.dto.request.Cost.Cost.class)
                        .retrieve()
                        .bodyToMono(com.mstudent.model.dto.request.Cost.Cost.class).block();
//                    costRepository.save(cost);
                }
            } else {
                costCheck.setPrice(costCheck.getPrice().add(attendance.getPrice()));
                costRepository.save(costCheck);
            }

            //end check cost

            AttendanceKafkaMessage attendanceKafkaMessage = new AttendanceKafkaMessage();
            attendanceKafkaMessage.setDate(attendance.getDate().toString());
            attendanceKafkaMessage.setState(attendance.getState());
            attendanceKafkaMessage.setRoomName(attendance.getRoom().getName());
            attendanceKafkaMessage.setStudentName(attendance.getStudent().getFullName());
            attendanceKafkaMessage.setType(KafkaMessageType.EMAIL.getValue());
            ObjectMapper Obj = new ObjectMapper();
            String jsonStr = null;
            try {
                jsonStr = Obj.writeValueAsString(attendanceKafkaMessage);
//                kafkaTemplate.send("email-topic",jsonStr);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });
        return attendanceMapper.listEntityToResponse(attendances);
    }

    public Attendance getById(Long id) throws NotFoundException {
        log.info("Start retrieve attendance with id = {}", id);
        Optional<Attendance> attendanceOptional = attendanceRepository.findById(id);
        if(!attendanceOptional.isPresent()){
            throw new NotFoundException("exception.notfound");
        }
        return attendanceOptional.get();
    }

    public List<AttendanceResponse> getListByRoomAndDate(Long roomId, LocalDate date) throws NotFoundException {
        log.info("Start retrieve attendance with room-id = {} and date = {}", roomId, date);
        List<Attendance> attendances = attendanceRepository.findAllByRoomIdAndDate(roomId, date);
        if(CollectionUtils.isEmpty(attendances)){
            throw new NotFoundException("exception.list.null");
        }
        return attendanceMapper.listEntityToResponse(attendances);
    }

    public List<AttendanceResponse> getAll() throws NotFoundException {
        log.info("Start retrieve all attendances");
        List<Attendance> attendances = attendanceRepository.findAll();
        if(CollectionUtils.isEmpty(attendances)){
            throw new NotFoundException("exception.list.null");
        }
        return attendanceMapper.listEntityToResponse(attendances);
    }

    public List<AttendanceResponse> update(UpdateAttendanceRequest updateAttendanceRequest)
        throws NotFoundException {
        log.info("Start update attendance at date = {}",updateAttendanceRequest.getDate());
        List<Attendance> attendances = processUpdateRequest(updateAttendanceRequest);
        if(CollectionUtils.isEmpty(attendances)){
            throw new NotFoundException("exception.list.null");
        }
        attendanceRepository.saveAll(attendances);
        return attendanceMapper.listEntityToResponse(attendances);
    }

    public List<AttendanceResponse> getListFilterByDate(LocalDate fromDate, LocalDate toDate, Long roomId)
        throws NotFoundException {
        List<Attendance> attendances =  attendanceRepository.findByRoomIdAndRangeDate(fromDate, toDate, roomId);
        if(CollectionUtils.isEmpty(attendances)){
            throw new NotFoundException("exception.list.null");
        }
        return attendanceMapper.listEntityToResponse(attendances);
    }

    public List<AttendanceResponse> getListToProcessPrice(LocalDate fromDate, LocalDate toDate, Long roomId, Long studentId)
        throws NotFoundException {
        List<Attendance> attendances =  attendanceRepository.findAllByRoomIdAndStudentIdWithRangeDate(fromDate, toDate, roomId,studentId);
        if(CollectionUtils.isEmpty(attendances)){
            throw new NotFoundException("exception.list.null");
        }
        return attendanceMapper.listEntityToResponse(attendances);
    }

    private Mono<Throwable> handleErrors(ClientResponse response ){
        return response.bodyToMono(String.class)
                .onErrorResume(e -> Mono.error(new NotFoundException("Correct ID is required"+e)))
                .onErrorReturn("Hello Stranger").flatMap(body -> {
            log.error("LOg errror");
            return Mono.error(new Exception());
        });
    }


    public List<Attendance> mapRequestToEntity(CreateAttendanceRequest createAttendanceRequest){
        List<StudentAttendance> studentAttendances = createAttendanceRequest.getStudentAttendances();
        List<Attendance> attendances = new ArrayList<>();
        studentAttendances.forEach(studentAttendance -> {
            Attendance attendance = new Attendance();
            attendance.setStudent(studentRepository.findById(studentAttendance.getId()).get());
            attendance.setRoom(roomRepository.findById(createAttendanceRequest.getRoomId()).get());
            attendance.setDate(dateUtils.changeFormatDate(createAttendanceRequest.getDate()));
            attendance.setPrice(roomRepository.findById(createAttendanceRequest.getRoomId()).get().getPricePerLesson());
            attendance.setMonth(dateUtils.getMonthAndYear(Date.from(OffsetDateTime.now().toInstant())));
            attendance.setState(studentAttendance.getState());
            attendances.add(attendance);
        });
        return attendances;
    }

    public List<Attendance> processUpdateRequest(UpdateAttendanceRequest updateAttendanceRequest){
        List<StudentAttendance> studentAttendances = updateAttendanceRequest.getStudentAttendances();
        studentAttendances.forEach(studentAttendance -> {
            log.info("Get attendance of student at lesson need to update");
            Attendance attendance = attendanceRepository.findByRoomIdAndStudentIdWithDateAndState(
                updateAttendanceRequest.getRoomId(), studentAttendance.getId(),
                    dateUtils.changeFormatDate(updateAttendanceRequest.getDate()), studentAttendance.getState());
            // Neu khong tim thay thi tao moi 1 thong tin diem danh cua sinh vien do
            // Sinh vien khac state se khong tim thay
            if(Objects.isNull(attendance)){
                log.info("Start update attendance for student");
                Attendance attendanceToUpdate = attendanceRepository.findByRoomIdAndStudentIdWithDate(
                    updateAttendanceRequest.getRoomId(), studentAttendance.getId(),
                        dateUtils.changeFormatDate(updateAttendanceRequest.getDate()));
                if(!Objects.isNull(attendanceToUpdate)){
//                    attendanceRepository.delete(attendanceToRemove);
                    log.info("Start edit old attendance for student with id = {}", attendanceToUpdate.getStudent().getId());
                    // Sau khi xoa thong tin diem danh cu se them moi
//                    Attendance attendanceUpdate = new Attendance();
                    attendanceToUpdate.setStudent(studentRepository.findById(studentAttendance.getId()).get());
                    attendanceToUpdate.setRoom(roomRepository.findById(updateAttendanceRequest.getRoomId()).get());
                    attendanceToUpdate.setDate(dateUtils.changeFormatDate(updateAttendanceRequest.getDate()));
                    attendanceToUpdate.setMonth(dateUtils.getMonthAndYear(updateAttendanceRequest.getDate()));
                    attendanceToUpdate.setState(studentAttendance.getState());
                    attendanceToUpdate.setPrice(roomRepository.findById(updateAttendanceRequest.getRoomId()).get().getPricePerLesson());
                    log.info("Save new attendance for student with id = {}", attendanceToUpdate.getStudent().getId());
                    attendanceRepository.save(attendanceToUpdate);
                    //will be moved to another service
                    Cost costCheck = costRepository.findAllByRoomIdAndStudentIdAndMonth(updateAttendanceRequest.getRoomId(), attendanceToUpdate.getStudent().getId(),
                        attendanceToUpdate.getMonth());
                    if(!Objects.isNull(costCheck)){
                        if(attendanceToUpdate.getState().equals(AttendanceState.PRESENT.getValue())){
                            costCheck.setPrice(costCheck.getPrice().add(attendanceToUpdate.getPrice()));
                        }else {
                            costCheck.setPrice(costCheck.getPrice().subtract(attendanceToUpdate.getPrice()));
                        }
                        costRepository.save(costCheck);
                    } else {
                        Cost cost = new Cost();
                        cost.setState(CostState.NOT_YET.getValue());
                        cost.setStudent(attendance.getStudent());
                        cost.setRoom(roomRepository.findById(updateAttendanceRequest.getRoomId()).get());
                        cost.setPrice(attendance.getPrice());
                        cost.setMonth(attendance.getMonth());
                        costRepository.save(cost);
                    }
                }
                // Gui kafka tai day
            }
        });
        return attendanceRepository.findAllByRoomIdAndDate(updateAttendanceRequest.getRoomId(),
                dateUtils.changeFormatDate(updateAttendanceRequest.getDate()));
    }

//    public boolean processPricePerMonth(Long roomId, Date date) throws NotFoundException {
//        // Lay danh sach diem danh cua thang
//        log.info("Start process to count price per month for student");
//        log.info("Start retrieve room with id = %s",roomId);
//        Room room = roomRepository.findById(roomId).get();
//        if(Objects.isNull(room)){
//            throw new NotFoundException("exception.notfound");
//        }
//        List<Student> students = room.getStudents();
//        if(CollectionUtils.isEmpty(students)){
//            throw new NotFoundException("exception.list.null");
//        }
//        List<Cost> costs = new ArrayList<>();
//
//        students.forEach(student -> {
//            log.info("Start create cost per month for student with id = %s", student.getId());
//            List<Attendance> attendancesOfStudents =
//                attendanceRepository.findAllByRoomIdAndStudentIdAndMonthAndState( getMonthAndYear(date),
//                    roomId, student.getId(), AttendanceState.PRESENT.getValue());
//            Cost cost = new Cost();
//            cost.setPrice(room.getPricePerLesson().subtract(
//                BigDecimal.valueOf(attendancesOfStudents.size())));
//            LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//            int month = localDate.getMonthValue();
//            cost.setMonth(month);
//            cost.setStudent(student);
//            cost.setNumberOfLesson(attendancesOfStudents.size());
//            cost.setRoom(room);
//            cost.setState(CostState.NOT_YET.getValue());
//            costs.add(cost);
//        });
//        costRepository.saveAll(costs);
//        log.info("Finished create cost for student in room with id =%s", roomId);
//        if(students.size() >= costs.size()){
//            return false;
//        }
//
//        return true;
//    }


}
