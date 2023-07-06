package br.com.compassuol.pb.challenge.msnotification.consumer;

import br.com.compassuol.pb.challenge.msnotification.entities.Email;
import br.com.compassuol.pb.challenge.msnotification.service.EmailService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import  br.com.compassuol.pb.challenge.msnotification.dto.EmailDTO;
@Component
public class EmailConsumer {

    @Autowired
    EmailService emailService;

    @RabbitListener(queues = "${spring.rabbitmq.queue}")
    public void listen(@Payload EmailDTO emailDto) {
        Email email = new Email();
        BeanUtils.copyProperties(emailDto, email);
        emailService.sendEmail(email);
        System.out.println("Email Status: " + email.getStatusEmail().toString());
    }
}