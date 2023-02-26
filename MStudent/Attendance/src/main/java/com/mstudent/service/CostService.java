package com.mstudent.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mstudent.enums.CostState;
import com.mstudent.enums.KafkaMessageType;
import com.mstudent.exception.NotFoundException;
import com.mstudent.mapper.CostMapper;
import com.mstudent.model.dto.request.Attendance.AttendanceKafkaMessage;
import com.mstudent.model.dto.request.Cost.CostFindByRoomAndMonth;
import com.mstudent.model.dto.response.Cost.CostResponse;
import com.mstudent.model.entity.Cost;
import com.mstudent.repository.CostRepository;
import java.time.OffsetDateTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class CostService {

  @Autowired
  KafkaTemplate<String, String> kafkaTemplate;
  private final CostRepository costRepository;
  private final CostMapper costMapper;

  public CostService(CostRepository costRepository, CostMapper costMapper) {
    this.costRepository = costRepository;
    this.costMapper = costMapper;
  }

  public Cost insert(Cost cost){
    return costRepository.save(cost);
  }

//  public CostResponse updateDone(Long id) throws NotFoundException {
//    Cost cost = costRepository.findById(id).get();
//    if(Objects.isNull(cost)){
//      throw new NotFoundException("exception.notfound");
//    }
//    AttendanceKafkaMessage attendanceKafkaMessage = new AttendanceKafkaMessage();
//    attendanceKafkaMessage.setDate(String.valueOf(Date.from(OffsetDateTime.now().toInstant())));
//    attendanceKafkaMessage.setState(cost.getState());
//    attendanceKafkaMessage.setRoomName(cost.getRoom().getName());
//    attendanceKafkaMessage.setStudentName(cost.getStudent().getFullName());
//    attendanceKafkaMessage.setType(KafkaMessageType.COST.getValue());
//    cost.setState(CostState.DONE.getValue());
//    costRepository.save(cost);
//    ObjectMapper Obj = new ObjectMapper();
//    String jsonStr = null;
//    try {
//      jsonStr = Obj.writeValueAsString(attendanceKafkaMessage);
//      kafkaTemplate.send("cost-topic",jsonStr);
//    } catch (JsonProcessingException e) {
//      throw new RuntimeException(e);
//    }
//    return costMapper.entityToResponse(cost);
//  }
//
  public CostResponse getById(Long id) throws NotFoundException {
    Cost cost = costRepository.findById(id).get();
    if(Objects.isNull(cost)){
      throw new NotFoundException("exception.notfound");
    }
    return costMapper.entityToResponse(cost);
  }
//
//  public List<CostResponse> getAllByMonthAndRoom(CostFindByRoomAndMonth costFindByRoomAndMonth)
//      throws NotFoundException {
//    List<Cost> costs = costRepository.findAllByRoomAndMonth(costFindByRoomAndMonth.getRoomId(), costFindByRoomAndMonth.getMonth());
//    if(CollectionUtils.isEmpty(costs)){
//      throw new NotFoundException("exception.list.null");
//    }
//    return costMapper.listEntityToResponse(costs);
//  }

}
