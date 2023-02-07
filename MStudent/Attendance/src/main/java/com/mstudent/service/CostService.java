package com.mstudent.service;

import com.mstudent.model.entity.Cost;
import com.mstudent.repository.CostRepository;
import org.springframework.stereotype.Service;

@Service
public class CostService {
  private final CostRepository costRepository;

  public CostService(CostRepository costRepository) {
    this.costRepository = costRepository;
  }

  public Cost insert(Cost cost){
    return costRepository.save(cost);
  }

  public Cost update(Cost cost){
    Cost cost1 = costRepository.findById(cost.getId()).get();
    cost1.setId(cost.getId());
    cost1.setRoom(cost.getRoom());
    return costRepository.save(cost1);
  }

  public Cost getById(Long id){
    return costRepository.findById(id).get();
  }

}
