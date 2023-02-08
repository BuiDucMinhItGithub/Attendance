package com.mstudent.service;

import com.mstudent.enums.CostState;
import com.mstudent.exception.NotFoundException;
import com.mstudent.model.entity.Cost;
import com.mstudent.repository.CostRepository;
import java.util.Objects;
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

  public Cost updateDone(Long id) throws NotFoundException {
    Cost cost = costRepository.findById(id).get();
    if(Objects.isNull(cost)){
      throw new NotFoundException("exception.notfound");
    }
    cost.setState(CostState.DONE.getValue());
    return costRepository.save(cost);
  }

  public Cost getById(Long id) throws NotFoundException {
    Cost cost = costRepository.findById(id).get();
    if(Objects.isNull(cost)){
      throw new NotFoundException("exception.notfound");
    }
    return cost;
  }

}
