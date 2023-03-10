package com.mstudent.service;

import static com.mstudent.specification.RoomSpecification.hasRoomWithTeacherId;

import com.mstudent.enums.RoomState;
import com.mstudent.exception.NotFoundException;
import com.mstudent.mapper.RoomMapper;
import com.mstudent.model.dto.request.Room.CreateRoomRequest;
import com.mstudent.model.dto.request.Room.UpdateRoomRequest;
import com.mstudent.model.dto.response.Room.RoomCreateResponse;
import com.mstudent.model.dto.response.Room.RoomResponse;
import com.mstudent.model.dto.response.Room.RoomShortResponse;
import com.mstudent.model.dto.response.Room.RoomUpdateResponse;
import com.mstudent.model.entity.Room;
import com.mstudent.repository.RoomRepository;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class RoomService {

    private final RoomRepository roomRepository;
    private final RoomMapper roomMapper;

    public RoomService(RoomRepository roomRepository, RoomMapper roomMapper) {
        this.roomRepository = roomRepository;
        this.roomMapper = roomMapper;
    }

    public RoomCreateResponse insert(CreateRoomRequest createRoomRequest){
        Room room = roomMapper.createRequestToEntity(createRoomRequest);
        room.setState(RoomState.OPEN.getValue());
        room.setStartDate(LocalDate.now());
        roomRepository.save(room);
        return roomMapper.entityToCreateResponse(room);
    }

    public RoomUpdateResponse update(UpdateRoomRequest updateRoomRequest) throws NotFoundException {
        Room room = roomRepository.findById(updateRoomRequest.getId()).get();
        if(Objects.isNull(room)){
            throw new NotFoundException("exception.notfound");
        }
        Room roomUpdate = roomMapper.updateRequestToEntity(updateRoomRequest);
        roomUpdate.setState(room.getState());
        roomRepository.save(roomUpdate);
        return roomMapper.entityToUpdateResponse(roomUpdate);
    }

    public RoomResponse getById(Long id) throws NotFoundException {
        Room room = roomRepository.findById(id).get();
        if(Objects.isNull(room)){
            throw new NotFoundException("exception.notfound");
        }
        return roomMapper.entityToResponse(room);
    }

    public List<RoomResponse> getRoomByTeacher(Long id) throws NotFoundException {
        Specification<Room> specification = hasRoomWithTeacherId(id);
        List<Room> rooms =  roomRepository.findAll(specification);
        if(CollectionUtils.isEmpty(rooms)){
            throw new NotFoundException("exception.list.null");
        }
        return roomMapper.listEntityToResponse(rooms);
    }

    public List<RoomShortResponse> getAll() throws NotFoundException {
        List<Room> rooms = roomRepository.findAll();
        if(CollectionUtils.isEmpty(rooms)){
            throw new NotFoundException("exception.list.null");
        }
        return roomMapper.listEntityToShortResponse(rooms);
    }
}
