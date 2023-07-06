package br.com.compassuol.pb.challenge.msnotification.service;

import br.com.compassuol.pb.challenge.msnotification.entities.Email;
import br.com.compassuol.pb.challenge.msnotification.enums.EmailState;
import br.com.compassuol.pb.challenge.msnotification.repository.EmailRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import org.springframework.mail.javamail.JavaMailSender;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class EmailService {

    @Autowired
    EmailRepository emailRepository;

    @Autowired
    private JavaMailSender emailSender;

    @Transactional
    public Email sendEmail(Email emailModel) {
        emailModel.setSendDateEmail(LocalDateTime.now());
        try{
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(emailModel.getEmailFrom());
            message.setTo(emailModel.getEmailTo());
            message.setSubject(emailModel.getSubject());
            message.setText(emailModel.getText());
            emailSender.send(message);

            emailModel.setStatusEmail(EmailState.SENT);
        } catch (MailException e){
            emailModel.setStatusEmail(EmailState.ERROR);
        } finally {
            return emailRepository.save(emailModel);
        }
    }

    public Page<Email> findAll(Pageable pageable) {
        return  emailRepository.findAll(pageable);
    }

    public Optional<Email> findById(UUID emailId) {
        return emailRepository.findById(emailId);
    }
}