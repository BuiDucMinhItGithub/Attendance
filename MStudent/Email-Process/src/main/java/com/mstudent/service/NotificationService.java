package com.mstudent.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mstudent.config.MyConstants;
import com.mstudent.enums.AttendanceState;
import com.mstudent.enums.EmailType;
import com.mstudent.enums.TypeNotification;
import com.mstudent.model.Attendance;
import com.mstudent.model.Cost;
import com.mstudent.model.Notification;
import com.mstudent.repository.NotificationRepository;
import java.time.OffsetDateTime;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final JavaMailSender javaMailSender;

    public NotificationService(NotificationRepository notificationRepository,
        JavaMailSender javaMailSender) {
        this.notificationRepository = notificationRepository;
        this.javaMailSender = javaMailSender;
    }

    public void sendEmailAttendance(String messageReceive) throws JsonProcessingException {
        ObjectMapper obj = new ObjectMapper();
        Notification notification = new Notification();
        Attendance attendance = obj.readValue(messageReceive, Attendance.class);
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
        sendNotification(notification);
    }

    public void sendNotification(Notification notification){
        // Create a Simple MailMessage and send message
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(MyConstants.MY_EMAIL);
        message.setTo(MyConstants.FRIEND_EMAIL);
        message.setSubject(notification.getTitle());
        message.setText(notification.getContent());
        javaMailSender.send(message);
    }

    public void sendEmailCost(String messageReceive) throws JsonProcessingException {
        ObjectMapper obj = new ObjectMapper();
        Notification notification = new Notification();
        Cost cost = obj.readValue(messageReceive, Cost.class);
        notification.setContent("Học sinh "+ cost.getStudentName()+ "đã đóng tiền học "+cost.getMonth());
        notification.setDate(cost.getDate());
        notification.setTitle("Thông báo tiền học tháng "+cost.getMonth());
        notification.setRoomName(cost.getRoomName());
        notification.setStudentName(cost.getStudentName());
        sendNotification(notification);
    }
}
