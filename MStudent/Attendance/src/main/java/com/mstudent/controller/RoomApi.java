package com.mstudent.controller;

import com.mstudent.exception.NotFoundException;
import com.mstudent.model.dto.request.CreateRoomRequest;
import com.mstudent.model.dto.request.UpdateRoomRequest;
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

  @GetMapping
  public List<Room> getAllRoomByTeacher(){
    try{
      return roomService.getRoomByTeacher();
    } catch (Exception ex){
      System.out.println(ex.getMessage());
    }
    return null;
  }
  @PostMapping
  public ResponseEntity<Room> insert(@RequestBody CreateRoomRequest createRoomRequest){
    return ResponseEntity.ok(roomService.insert(createRoomRequest));
  }
  @PutMapping
  public ResponseEntity<Room> update(@RequestBody UpdateRoomRequest updateRoomRequest) throws NotFoundException {
    return ResponseEntity.ok(roomService.update(updateRoomRequest));
  }
  @GetMapping("/{id}")
  public ResponseEntity<Room> getOne(@PathVariable Long id){
    return ResponseEntity.ok(roomService.getById(id));
  }
}