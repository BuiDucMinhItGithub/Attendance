package com.mstudent.controller;

import com.mstudent.exception.NotFoundException;
import com.mstudent.model.dto.request.Attendance.AttendanceRangeDateRequest;
import com.mstudent.model.dto.request.Attendance.AttendanceToProcessCostRequest;
import com.mstudent.model.dto.request.Attendance.AttendanceTodayRequest;
import com.mstudent.model.dto.request.Attendance.CreateAttendanceRequest;
import com.mstudent.model.dto.request.Attendance.UpdateAttendanceRequest;
import com.mstudent.model.dto.response.Attendance.AttendanceResponse;
import com.mstudent.service.AttendanceService;
import java.util.List;

import com.mstudent.utils.DateUtils;
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
  private final DateUtils dateUtils;

  public AttendanceApi(AttendanceService attendanceService, DateUtils dateUtils) {
    this.attendanceService = attendanceService;
    this.dateUtils = dateUtils;
  }

  @PutMapping
  public List<AttendanceResponse> update(@RequestBody UpdateAttendanceRequest updateAttendanceRequest)
      throws NotFoundException {
    return attendanceService.update(updateAttendanceRequest);
  }

  @PostMapping
  public List<AttendanceResponse> insert(@RequestBody CreateAttendanceRequest createAttendanceRequest)
      throws NotFoundException {
    return attendanceService.insert(createAttendanceRequest);
  }

  @PostMapping("/attendance-today")
  public List<AttendanceResponse> getListAttendanceByRoomAndDate(@RequestBody AttendanceTodayRequest attendanceTodayRequest)
      throws NotFoundException {
    return attendanceService.getListByRoomAndDate(attendanceTodayRequest.getRoomId(),dateUtils.changeFormatDate(attendanceTodayRequest.getDate()));
  }

  @GetMapping("/list-all")
  public List<AttendanceResponse> getAll() throws NotFoundException {
    return attendanceService.getAll();
  }

  @PostMapping("/list-range-date")
  public List<AttendanceResponse> getAllWithRangeOfDate(@RequestBody AttendanceRangeDateRequest attendanceRangeDateRequest) throws NotFoundException {
    return attendanceService.getListFilterByDate(dateUtils.changeFormatDate(attendanceRangeDateRequest.getFromDate()),
            dateUtils.changeFormatDate(attendanceRangeDateRequest.getToDate()),
        attendanceRangeDateRequest.getRoomId());
  }

  @PostMapping("/list-to-process")
  public List<AttendanceResponse> getListToProcessCost(@RequestBody AttendanceToProcessCostRequest attendanceToProcessCostRequest)
      throws NotFoundException {
    return attendanceService.getListToProcessPrice(dateUtils.changeFormatDate(attendanceToProcessCostRequest.getFromDate()),
            dateUtils.changeFormatDate(attendanceToProcessCostRequest.getToDate()),
        attendanceToProcessCostRequest.getRoomId(), attendanceToProcessCostRequest.getStudentId());
  }
}
