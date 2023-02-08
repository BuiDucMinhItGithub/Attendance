package com.mstudent.service;

import com.mstudent.enums.CostState;
import com.mstudent.exception.NotFoundException;
import com.mstudent.mapper.CostMapper;
import com.mstudent.model.dto.request.Cost.CostFindByRoomAndMonth;
import com.mstudent.model.dto.response.Cost.CostResponse;
import com.mstudent.model.entity.Cost;
import com.mstudent.repository.CostRepository;
import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class CostService {
  private final CostRepository costRepository;
  private final CostMapper costMapper;

  public CostService(CostRepository costRepository, CostMapper costMapper) {
    this.costRepository = costRepository;
    this.costMapper = costMapper;
  }

  public Cost insert(Cost cost){
    return costRepository.save(cost);
  }

  public CostResponse updateDone(Long id) throws NotFoundException {
    Cost cost = costRepository.findById(id).get();
    if(Objects.isNull(cost)){
      throw new NotFoundException("exception.notfound");
    }
    cost.setState(CostState.DONE.getValue());
    costRepository.save(cost);
    return costMapper.entityToResponse(cost);
  }

  public CostResponse getById(Long id) throws NotFoundException {
    Cost cost = costRepository.findById(id).get();
    if(Objects.isNull(cost)){
      throw new NotFoundException("exception.notfound");
    }
    return costMapper.entityToResponse(cost);
  }

  public List<CostResponse> getAllByMonthAndRoom(CostFindByRoomAndMonth costFindByRoomAndMonth)
      throws NotFoundException {
    List<Cost> costs = costRepository.findAllByRoomAndMonth(costFindByRoomAndMonth.getRoomId(), costFindByRoomAndMonth.getMonth());
    if(CollectionUtils.isEmpty(costs)){
      throw new NotFoundException("exception.list.null");
    }
    return costMapper.listEntityToResponse(costs);
  }

}
