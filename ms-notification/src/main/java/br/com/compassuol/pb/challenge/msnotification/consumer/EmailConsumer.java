package br.com.compassuol.pb.challenge.msnotification.consumer;

import br.com.compassuol.pb.challenge.msnotification.entities.Email;
import br.com.compassuol.pb.challenge.msnotification.service.EmailService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;

@Component
@AllArgsConstructor
@Slf4j
public class EmailConsumer {

    @Autowired
    EmailService emailService;

    @Autowired
    private final RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = "ms-notification")
    public void listenUserCreation(String userEmail) {
        log.info("E-mail cadastrado: {}", userEmail);

        Email email = new Email();
        email.setEmailFrom("msnotification19@gmail.com");
        email.setEmailTo(userEmail);
        email.setSubject("Bem-vindo ao nosso sistema ");
        email.setText("Olá! O seu cadastro foi realizado com sucesso.");

        StringBuilder emailRepresentation = new StringBuilder();
        emailRepresentation.append("-------------------------------------------------\n");
        emailRepresentation.append("De: ").append(email.getEmailFrom()).append("\n");
        emailRepresentation.append("Para: ").append(email.getEmailTo()).append("\n");
        emailRepresentation.append("Assunto: ").append(email.getSubject()).append("\n");
        emailRepresentation.append("-------------------------------------------------\n");
        emailRepresentation.append(email.getText()).append("\n");
        emailRepresentation.append("-------------------------------------------------\n");

        System.out.println(emailRepresentation.toString());


        rabbitTemplate.convertAndSend("ms-notification", email.getSubject() + email.getText());

        emailService.sendEmail(email);
    }
}