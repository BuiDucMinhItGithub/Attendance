package com.mstudent.controller;

import com.mstudent.config.MyConstants;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1/notification")
public class NotificationController {

   private final JavaMailSender javaMailSender;
   private final KafkaTemplate<String, String> kafkaTemplate;

    public NotificationController(JavaMailSender javaMailSender, KafkaTemplate<String, String> kafkaTemplate) {
        this.javaMailSender = javaMailSender;
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = "email-topic", groupId = "group-id")
    public void sendSimpleEmail() {

        // Create a Simple MailMessage.
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(MyConstants.MY_EMAIL);
        message.setTo(MyConstants.FRIEND_EMAIL);
        message.setSubject("Thông báo từ hệ thống bán lẻ máy tính và phụ kiện HaNoi");
        message.setText("Đơn hàng của bạn ");

        // Send Message!
        javaMailSender.send(message);
    }
}
