package com.ecommerce.chomoi.service;

import com.ecommerce.chomoi.dto.email.SendEmailDto;
import com.ecommerce.chomoi.exception.AppException;
import com.ecommerce.chomoi.util.EmailTemplateUtil;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailService {
    @Value("${spring.mail.username}")
    private String systemEmail;

    private final JavaMailSender mailSender;
    private final EmailTemplateUtil emailTemplateUtil;

    public void sendEmail(SendEmailDto emailPayload) {
        var message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(emailPayload.getTo());
            helper.setSubject(emailPayload.getSubject());
            helper.setText(emailPayload.getText(), true);
            helper.setFrom(systemEmail);
            mailSender.send(message);
        } catch (MessagingException e) {
            System.out.print(e.toString());
            throw new AppException(HttpStatus.BAD_REQUEST, "Failed to send email", "mail-e-01");
        }
    }

    public void sendEmailToVerifyRegister(String toEmail, String verificationCode, String filePath) {
        String verifyUrl = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/auth/register/verify/{verificationCode}")
                .buildAndExpand(verificationCode)
                .toUriString();

        Map<String, String> placeholders = new HashMap<>();
        placeholders.put("verifyUrl", verifyUrl);

        String emailText = emailTemplateUtil.replaceValueInEmailTemplate(placeholders, filePath);
        SendEmailDto emailPayload = SendEmailDto.builder()
                .to(toEmail)
                .subject("Verity email to register")
                .text(emailText)
                .build();
        sendEmail(emailPayload);
    }

    public void sendEmailToWelcome(String toEmail, String filePath) {
        String emailText = emailTemplateUtil.emailTemplate(filePath);
        SendEmailDto emailPayload = SendEmailDto.builder()
                .to(toEmail)
                .subject("Secondhand Market welcome")
                .text(emailText)
                .build();
        sendEmail(emailPayload);
    }

    public void sendEmailToVerifyForgotPassword(String toEmail, String verificationCode, String filePath) {
        Map<String, String> placeholders = new HashMap<>();
        placeholders.put("verificationCode", verificationCode);
        String emailText = emailTemplateUtil.replaceValueInEmailTemplate(placeholders, filePath);
        SendEmailDto emailPayload = SendEmailDto.builder()
                .to(toEmail)
                .subject("Verity to create new password")
                .text(emailText)
                .build();
        sendEmail(emailPayload);
    }
}
