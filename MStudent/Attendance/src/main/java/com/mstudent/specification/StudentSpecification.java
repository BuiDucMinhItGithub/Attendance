package com.mstudent.specification;

import com.mstudent.model.entity.Room;
import com.mstudent.model.entity.Student;
import com.mstudent.model.entity.Teacher;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

public class StudentSpecification {
    public static Specification<Student> hasRoomWithId(Long id) {
        return (root, query, criteriaBuilder) -> {
            Join<Student, Room> studentRoomJoin = root.join("rooms");
            return criteriaBuilder.equal(studentRoomJoin.get("id"), id);
        };
    }
}
