package com.mstudent.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mstudent.config.MyConstants;
import com.mstudent.enums.AttendanceState;
import com.mstudent.model.Attendance;
import com.mstudent.model.Notification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1/notification")
@Slf4j
public class NotificationController {

  private final JavaMailSender javaMailSender;
  private final KafkaTemplate<String, String> kafkaTemplate;

  public NotificationController(JavaMailSender javaMailSender,
      KafkaTemplate<String, String> kafkaTemplate) {
    this.javaMailSender = javaMailSender;
    this.kafkaTemplate = kafkaTemplate;
  }

  @KafkaListener(topics = "email-topic", groupId = "group-id")
  public void sendSimpleEmail(String messageReceive) throws JsonProcessingException {
    ObjectMapper Obj = new ObjectMapper();
    Notification notification = new Notification();
    Attendance attendance = Obj.readValue(messageReceive, Attendance.class);
    String content = null;
    if (attendance.getState().equals(AttendanceState.ABSENT.getValue())) {
      content = "Học sinh " + attendance.getStudentName() + " không tham gia lớp học";
    } else if (attendance.getState().equals(AttendanceState.PRESENT.getValue())) {
      content = "Học sinh " + attendance.getStudentName() + " có tham gia lớp học";
    } else if (attendance.getState().equals(AttendanceState.ABSENT_WITH_PERMISSION.getValue())) {
      content = "Học sinh " + attendance.getStudentName() + " nghỉ học có phép";
    }
    notification.setContent(content);
    notification.setDate(attendance.getDate());
    notification.setTitle(
        "Thông tin điểm danh lớp " + attendance.getRoomName() + "ngày " + attendance.getDate());
    notification.setRoomName(attendance.getRoomName());
    notification.setStudentName(attendance.getStudentName());
    System.out.println(notification.getRoomName());
    // Create a Simple MailMessage.
    SimpleMailMessage message = new SimpleMailMessage();

    message.setFrom(MyConstants.MY_EMAIL);
    message.setTo(MyConstants.FRIEND_EMAIL);
    message.setSubject(notification.getTitle());
    message.setText(notification.getContent());

    // Send Message!
    javaMailSender.send(message);

  }

}
