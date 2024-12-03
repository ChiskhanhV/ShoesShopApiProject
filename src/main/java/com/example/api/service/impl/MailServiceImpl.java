package com.example.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.example.api.model.Mail;
import com.example.api.service.MailService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

@Service
public class MailServiceImpl implements MailService{

	@Autowired
	private JavaMailSender javaMailSender;
	
	@Override
	public void sendEmail(Mail mail) 
	{
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		try {
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
			mimeMessageHelper.setSubject(mail.getMailSubject());
			mimeMessageHelper.setFrom(new InternetAddress(mail.getMailFrom()));
			mimeMessageHelper.setTo(mail.getMailTo());
			// Xử lý kiểu nội dung (plain text hoặc HTML)
            boolean isHtml = "text/html".equalsIgnoreCase(mail.getContentType());
            mimeMessageHelper.setText(mail.getMailContent(), isHtml);
			javaMailSender.send(mimeMessageHelper.getMimeMessage());
		}
		catch (MessagingException e) {
			e.printStackTrace();
		}
	}

}
