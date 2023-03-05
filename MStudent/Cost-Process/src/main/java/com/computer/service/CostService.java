package com.computer.service;

import com.computer.model.Cost;
import com.computer.repository.CostRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

@Service
public class CostService {

  private final CostRepository costRepository;

  public CostService(CostRepository costRepository) {
    this.costRepository = costRepository;
  }

  public void insert(Cost cost){
    costRepository.save(cost);
  }

  public Cost getById(Long id){
    return costRepository.findById(id).get();
  }

  public Cost getByRoomIdAndStudentIdAndMonth(Long roomId, Long studentId, String month){
    return costRepository.findByRoomIdAndStudentIdAndMonth(roomId, studentId, month);
  }

  public void update(Cost cost){
    Cost cost1 = costRepository.findById(cost.getId()).get();
    cost1.setPrice(cost.getPrice());
    costRepository.save(cost1);
  }
}
