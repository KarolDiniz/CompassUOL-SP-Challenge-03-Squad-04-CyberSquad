package br.com.compassuol.pb.challenge.msuser.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class UserConsumer {

    @RabbitListener(queues = "ms-notification")
    public void receiveEmailMessage(String emailMessage) {
        System.out.println("Mensagem recebida do tópico 'ms-users':");
        System.out.println(emailMessage);
    }
}

