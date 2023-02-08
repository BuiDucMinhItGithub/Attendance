package com.mstudent.controller;

import com.mstudent.exception.NotFoundException;
import com.mstudent.model.dto.request.Cost.CostFindByRoomAndMonth;
import com.mstudent.model.dto.response.Cost.CostResponse;
import com.mstudent.service.CostService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/cost")
public class CostApi {
  private final CostService costService;

  public CostApi(CostService costService) {
    this.costService = costService;
  }

  @PutMapping
  public CostResponse update(Long id) throws NotFoundException {
    return costService.updateDone(id);
  }

  @GetMapping("/{id}")
  public CostResponse getOne(@PathVariable Long id) throws NotFoundException {
    return costService.getById(id);
  }

  @PostMapping("/list-in-month")
  public List<CostResponse> getAll(@RequestBody CostFindByRoomAndMonth costFindByRoomAndMonth)
      throws NotFoundException {
    return costService.getAllByMonthAndRoom(costFindByRoomAndMonth);
  }
}
