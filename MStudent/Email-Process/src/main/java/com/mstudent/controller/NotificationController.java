package com.mstudent.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mstudent.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1/notification")
@Slf4j
public class NotificationController {

  private final NotificationService notificationService;

  public NotificationController(NotificationService notificationService) {
    this.notificationService = notificationService;
  }

  @KafkaListener(topics = "email-topic", groupId = "group-id")
  public void sendEmailForAttendance(String messageReceive) throws JsonProcessingException {
    notificationService.sendEmailAttendance(messageReceive);
  }

  @KafkaListener(topics = "cost-topic", groupId = "group-id")
  public void sendEmailForCost(String messageReceive) throws JsonProcessingException {
    notificationService.sendEmailCost(messageReceive);
  }

}
