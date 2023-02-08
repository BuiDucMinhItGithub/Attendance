package com.mstudent.controller;

import com.mstudent.exception.NotFoundException;
import com.mstudent.model.entity.Cost;
import com.mstudent.service.CostService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
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
  public Cost update(Long id) throws NotFoundException {
    return costService.updateDone(id);
  }

  @GetMapping("/{id}")
  public Cost getOne(@PathVariable Long id) throws NotFoundException {
    return costService.getById(id);
  }
}
