package com.mstudent.controller;

import com.mstudent.exception.NotFoundException;
import com.mstudent.model.dto.request.AttendanceRangeDateRequest;
import com.mstudent.model.dto.request.AttendanceToProcessCostRequest;
import com.mstudent.model.dto.request.AttendanceTodayRequest;
import com.mstudent.model.dto.request.CreateAttendanceRequest;
import com.mstudent.model.dto.request.UpdateAttendanceRequest;
import com.mstudent.model.entity.Attendance;
import com.mstudent.service.AttendanceService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/attendance")
public class AttendanceApi {

  private final AttendanceService attendanceService;

  public AttendanceApi(AttendanceService attendanceService) {
    this.attendanceService = attendanceService;
  }

  @PutMapping
  public List<Attendance> update(@RequestBody UpdateAttendanceRequest updateAttendanceRequest){
    return attendanceService.update(updateAttendanceRequest);
  }

  @PostMapping
  public List<Attendance> insert(@RequestBody CreateAttendanceRequest createAttendanceRequest){
    return attendanceService.insert(createAttendanceRequest);
  }

  @PostMapping("/attendance-today")
  public List<Attendance> getListAttendanceByRoomAndDate(@RequestBody AttendanceTodayRequest attendanceTodayRequest)
      throws NotFoundException {
    return attendanceService.getListByRoomAndDate(attendanceTodayRequest.getRoomId(), attendanceTodayRequest.getDate());
  }

  @GetMapping("/list-all")
  public List<Attendance> getAll() throws NotFoundException {
    return attendanceService.getAll();
  }

  @PostMapping("/list-range-date")
  public List<Attendance> getAllWithRangeOfDate(@RequestBody AttendanceRangeDateRequest attendanceRangeDateRequest) throws NotFoundException {
    return attendanceService.getListFilterByDate(attendanceRangeDateRequest.getFromDate(), attendanceRangeDateRequest.getToDate(),
        attendanceRangeDateRequest.getRoomId());
  }

  @PostMapping("/list-to-process")
  public List<Attendance> getListToProcessCost(@RequestBody AttendanceToProcessCostRequest attendanceToProcessCostRequest)
      throws NotFoundException {
    return attendanceService.getListToProcessPrice(attendanceToProcessCostRequest.getFromDate(), attendanceToProcessCostRequest.getToDate(),
        attendanceToProcessCostRequest.getRoomId(), attendanceToProcessCostRequest.getStudentId());
  }

  @PostMapping("/process-cost")
  public boolean processCostPerMonthForStudent(@RequestBody AttendanceTodayRequest attendanceTodayRequest)
      throws NotFoundException {
    return attendanceService.processPricePerMonth(attendanceTodayRequest.getRoomId(), attendanceTodayRequest.getDate());
  }
}