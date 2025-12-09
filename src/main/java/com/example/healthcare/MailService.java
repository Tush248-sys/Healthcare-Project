package com.example.healthcare;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    private final JavaMailSender mailSender;

    public MailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public boolean sendPatientLoginOtp(String email, String otp) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("Patient Login OTP - Healthcare Registration Portal (Demo)");
            message.setText("Your OTP for patient login is: " + otp + " (valid for 5 minutes).");
            mailSender.send(message);
            return true;
        } catch (Exception ex) {
            // log in a real app
            return false;
        }
    }
}