package com.mstudent.specification;

import com.mstudent.model.entity.Room;
import com.mstudent.model.entity.Teacher;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

public class RoomSpecification {

  public static Specification<Room> hasRoomWithTeacherId(Long id) {
    return (root, query, criteriaBuilder) -> {
      Join<Teacher, Room> teacherRoomJoin = root.join("teacher");
      return criteriaBuilder.equal(teacherRoomJoin.get("id"), 1);
    };
  }

}
