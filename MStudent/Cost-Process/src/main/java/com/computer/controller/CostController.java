package com.computer.controller;

import com.computer.model.Cost;
import com.computer.service.CostService;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.Objects;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/cost")
public class CostController {
  private final CostService costService;

  public CostController(CostService costService) {
    this.costService = costService;
  }

  @GetMapping("/room-student-month")
  public Cost getByRoomIdAndStudentIdAndMonth(@RequestParam("roomId") Long roomId,@RequestParam("studentId") Long studentId,
      @RequestParam("month") String month)
      throws ClassNotFoundException {
    Cost cost = costService.getByRoomIdAndStudentIdAndMonth(roomId, studentId, month);
    if(Objects.isNull(cost)){
      throw new ClassNotFoundException("Khong tim thay");
    }
    return cost;
  }

  @PostMapping
  public void insert(@RequestBody Cost cost){
    costService.insert(cost);
  }
}
