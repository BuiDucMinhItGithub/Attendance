package com.mstudent.service;

import static com.mstudent.specification.RoomSpecification.hasRoomWithTeacherId;

import com.mstudent.enums.RoomState;
import com.mstudent.exception.NotFoundException;
import com.mstudent.mapper.RoomMapper;
import com.mstudent.model.dto.request.Room.CreateRoomRequest;
import com.mstudent.model.dto.request.Room.UpdateRoomRequest;
import com.mstudent.model.entity.Room;
import com.mstudent.repository.RoomRepository;
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

    public Room insert(CreateRoomRequest createRoomRequest){
        Room room = roomMapper.createRequestToEntity(createRoomRequest);
        room.setState(RoomState.OPEN.getValue());
        return roomRepository.save(room);
    }

    public Room update(UpdateRoomRequest updateRoomRequest) throws NotFoundException {
        Room room = roomRepository.findById(updateRoomRequest.getId()).get();
        Room roomUpdate = roomMapper.updateRequestToEntity(updateRoomRequest);
        if(Objects.isNull(room)){
            throw new NotFoundException("exception.notfound");
        }
        roomUpdate.setState(room.getState());
        return roomRepository.save(roomUpdate);
    }

    public Room getById(Long id) throws NotFoundException {
        Room room = roomRepository.findById(id).get();
        if(Objects.isNull(room)){
            throw new NotFoundException("exception.notfound");
        }
        return room;
    }

    public List<Room> getRoomByTeacher(Long id) throws NotFoundException {
        Specification<Room> specification = hasRoomWithTeacherId(id);
        List<Room> rooms =  roomRepository.findAll(specification);
        if(CollectionUtils.isEmpty(rooms)){
            throw new NotFoundException("exception.list.null");
        }
        return rooms;
    }
}
