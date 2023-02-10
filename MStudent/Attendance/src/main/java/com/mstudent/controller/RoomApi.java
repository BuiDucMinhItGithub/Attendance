package com.mstudent.controller;

import com.mstudent.exception.NotFoundException;
import com.mstudent.model.dto.request.Room.CreateRoomRequest;
import com.mstudent.model.dto.request.Room.UpdateRoomRequest;
import com.mstudent.model.dto.response.Room.RoomCreateResponse;
import com.mstudent.model.dto.response.Room.RoomResponse;
import com.mstudent.model.dto.response.Room.RoomUpdateResponse;
import com.mstudent.model.entity.Room;
import com.mstudent.service.RoomService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/room")
public class RoomApi {

  private final RoomService roomService;

  public RoomApi(RoomService roomService) {
    this.roomService = roomService;
  }

  @GetMapping("/teacher/{id}")
  public List<RoomResponse> getAllRoomByTeacher(@PathVariable Long id) throws NotFoundException {
      return roomService.getRoomByTeacher(id);
  }
  @PostMapping
  public ResponseEntity<RoomCreateResponse> insert(@RequestBody CreateRoomRequest createRoomRequest){
    return ResponseEntity.ok(roomService.insert(createRoomRequest));
  }
  @PutMapping
  public ResponseEntity<RoomUpdateResponse> update(@RequestBody UpdateRoomRequest updateRoomRequest) throws NotFoundException {
    return ResponseEntity.ok(roomService.update(updateRoomRequest));
  }
  @GetMapping("/{id}")
  public ResponseEntity<RoomResponse> getOne(@PathVariable Long id) throws NotFoundException {
    return ResponseEntity.ok(roomService.getById(id));
  }
}
