package com.mstudent.service;

import static com.mstudent.specification.RoomSpecification.hasRoomWithTeacherId;

import com.mstudent.enums.RoomState;
import com.mstudent.exception.NotFoundException;
import com.mstudent.mapper.RoomMapper;
import com.mstudent.model.dto.request.CreateRoomRequest;
import com.mstudent.model.dto.request.UpdateRoomRequest;
import com.mstudent.model.entity.Room;
import com.mstudent.repository.RoomRepository;
import java.util.List;
import java.util.Objects;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

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
            throw new NotFoundException("Không tìm thấy lớp");
        }
        roomUpdate.setState(room.getState());
        return roomRepository.save(roomUpdate);
    }

    public Room getById(Long id){
        return roomRepository.findById(id).get();
    }

    public List<Room> getRoomByTeacher(Long id){
        Specification<Room> specification = hasRoomWithTeacherId(id);
        return roomRepository.findAll(specification);
    }
}
