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
    private final AttendanceMapper attendanceMapper;
    private final DateUtils dateUtils;

    public AttendanceService(AttendanceRepository attendanceRepository, StudentRepository studentRepository, RoomRepository roomRepository,
                             AttendanceMapper attendanceMapper, DateUtils dateUtils) {
        this.attendanceRepository = attendanceRepository;
        this.studentRepository = studentRepository;
        this.roomRepository = roomRepository;
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
        // Check cost
        costCheck(attendances, createAttendanceRequest.getRoomId());
        // Send message Kafka
        attendances.forEach(attendance -> {
           sendMessageKafka(attendance);
        });
        return attendanceMapper.listEntityToResponse(attendances);
    }

    public AttendanceResponse getById(Long id) throws NotFoundException {
        log.info("Start retrieve attendance with id = {}", id);
        Optional<Attendance> attendanceOptional = attendanceRepository.findById(id);
        if(!attendanceOptional.isPresent()){
            throw new NotFoundException("exception.notfound");
        }
        return attendanceMapper.entityToResponse(attendanceOptional.get());
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
            attendance.setMonth(dateUtils.getMonthAndYear(createAttendanceRequest.getDate()));
            attendance.setState(studentAttendance.getState());
            attendances.add(attendance);
        });
        return attendances;
    }

    public List<Attendance> processUpdateRequest(UpdateAttendanceRequest updateAttendanceRequest){
        List<StudentAttendance> studentAttendances = updateAttendanceRequest.getStudentAttendances();
        studentAttendances.forEach(studentAttendance -> {
            log.info("Get attendance of student at lesson need to update");
            // Lay thong tin diem danh cua sinh vien theo request moi
            Attendance attendance = attendanceRepository.findByRoomIdAndStudentIdWithDateAndState(
                updateAttendanceRequest.getRoomId(), studentAttendance.getId(),
                    dateUtils.changeFormatDate(updateAttendanceRequest.getDate()), studentAttendance.getState());
            // Có ton tai thi update ko thi tao moi
            if(Objects.isNull(attendance)){
                log.info("Start update attendance for student");
                // Lay thong tin diem danh cua sinh vien theo ngay
                Attendance attendanceToUpdate = attendanceRepository.findByRoomIdAndStudentIdWithDate(
                    updateAttendanceRequest.getRoomId(), studentAttendance.getId(),
                        dateUtils.changeFormatDate(updateAttendanceRequest.getDate()));
                if(!Objects.isNull(attendanceToUpdate)){
                    log.info("Start edit old attendance for student with id = {}", attendanceToUpdate.getStudent().getId());
                    attendanceToUpdate.setStudent(studentRepository.findById(studentAttendance.getId()).get());
                    attendanceToUpdate.setRoom(roomRepository.findById(updateAttendanceRequest.getRoomId()).get());
                    attendanceToUpdate.setDate(dateUtils.changeFormatDate(updateAttendanceRequest.getDate()));
                    attendanceToUpdate.setMonth(dateUtils.getMonthAndYear(updateAttendanceRequest.getDate()));
                    attendanceToUpdate.setState(studentAttendance.getState());
                    attendanceToUpdate.setPrice(roomRepository.findById(updateAttendanceRequest.getRoomId()).get().getPricePerLesson());
                    log.info("Save new attendance for student with id = {}", attendanceToUpdate.getStudent().getId());
                    attendanceRepository.save(attendanceToUpdate);
                    // Cost check
                    Cost costCheck = getFromWebClient(webClient, attendanceToUpdate.getRoom().getId(), attendanceToUpdate.getStudent().getId(),
                            attendanceToUpdate.getMonth());

                    if(!Objects.isNull(costCheck.getId()) && !Objects.isNull(costCheck.getPrice())){
                        if(attendanceToUpdate.getState().equals(AttendanceState.PRESENT.getValue())){
                            costCheck.setPrice(costCheck.getPrice().add(attendanceToUpdate.getPrice()));
                        }else {
                            costCheck.setPrice(costCheck.getPrice().subtract(attendanceToUpdate.getPrice()));
                        }
                        putToWebClient(webClient,costCheck);
                    } else {
                        com.mstudent.model.dto.request.Cost.Cost cost = new com.mstudent.model.dto.request.Cost.Cost();
                        cost.setState(CostState.NOT_YET.getValue());
                        cost.setStudentId(studentRepository.findById(studentAttendance.getId()).get().getId());
                        cost.setRoomId(roomRepository.findById(updateAttendanceRequest.getRoomId()).get().getId());
                        cost.setPrice(attendanceToUpdate.getPrice());
                        cost.setMonth(attendanceToUpdate.getMonth());
                        // Save cost
                        postToWebClient(webClient, cost);
                    }
                } else {
                    com.mstudent.model.dto.request.Cost.Cost cost = new com.mstudent.model.dto.request.Cost.Cost();
                    cost.setState(CostState.NOT_YET.getValue());
                    cost.setStudentId(studentRepository.findById(studentAttendance.getId()).get().getId());
                    cost.setRoomId(roomRepository.findById(updateAttendanceRequest.getRoomId()).get().getId());
                    cost.setPrice(attendanceToUpdate.getPrice());
                    cost.setMonth(attendanceToUpdate.getMonth());
                    // Save cost
                    postToWebClient(webClient, cost);
                }
                // Gui kafka tai day
                sendMessageKafka(attendanceToUpdate);
            }
        });
        return attendanceRepository.findAllByRoomIdAndDate(updateAttendanceRequest.getRoomId(),
                dateUtils.changeFormatDate(updateAttendanceRequest.getDate()));
    }


    public void costCheck(List<Attendance> attendances, Long roomId){
        attendances.forEach(attendance -> {
            //check cost
            Cost costCheck = getFromWebClient(webClient,roomId, attendance.getStudent().getId(), attendance.getMonth());

            if(Objects.isNull(costCheck.getId()) && Objects.isNull(costCheck.getPrice())){
                if(attendance.getState().equals(AttendanceState.PRESENT.getValue())){
                    com.mstudent.model.dto.request.Cost.Cost cost = new com.mstudent.model.dto.request.Cost.Cost();
                    cost.setState(CostState.NOT_YET.getValue());
                    cost.setStudentId(attendance.getStudent().getId());
                    cost.setRoomId(roomRepository.findById(roomId).get().getId());
                    cost.setPrice(attendance.getPrice());
                    cost.setMonth(attendance.getMonth());
                    // save cost
                    postToWebClient(webClient, cost);
                }
            } else {
                costCheck.setPrice(costCheck.getPrice().add(attendance.getPrice()));
                // update cost
                putToWebClient(webClient,costCheck);
            }
        });
    }

    public void sendMessageKafka(Attendance attendance){
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
    }

    public Cost getFromWebClient(WebClient webClient, Long roomId, Long studentId, String month){
        Cost costCheck = null;
        try {
            costCheck = webClient.get()
                    .uri(uriBuilder -> uriBuilder.path("/room-student-month")
                            .queryParam("roomId", roomId)
                            .queryParam("studentId",studentId)
                            .queryParam("month",month)
                            .build())
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .onStatus(httpStatus -> HttpStatus.NOT_FOUND.equals(httpStatus), this::handleErrors)
                    .bodyToMono(Cost.class).onErrorReturn(new Cost()).block();
        } catch (Exception e){
            log.info(e.getMessage());
        }
        return costCheck;
    }

    public com.mstudent.model.dto.request.Cost.Cost postToWebClient(WebClient webClient, com.mstudent.model.dto.request.Cost.Cost cost){
        return webClient.post()
                .uri(uriBuilder -> uriBuilder.build())
                .body(Mono.just(cost), com.mstudent.model.dto.request.Cost.Cost.class)
                .retrieve()
                .bodyToMono(com.mstudent.model.dto.request.Cost.Cost.class).block();
    }

    public com.mstudent.model.dto.request.Cost.Cost putToWebClient(WebClient webClient, Cost cost){
        return webClient.put()
                .uri(uriBuilder -> uriBuilder.build())
                .body(Mono.just(cost), com.mstudent.model.dto.request.Cost.Cost.class)
                .retrieve()
                .bodyToMono(com.mstudent.model.dto.request.Cost.Cost.class).block();
    }


}
