package br.com.compassuol.pb.challenge.msauthorization.services;

import br.com.compassuol.pb.challenge.msauthorization.entities.User;
import br.com.compassuol.pb.challenge.msauthorization.feignClients.UserFeignClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    private static Logger logger = LoggerFactory.getLogger(UserService.class);

    //Autowired
    UserFeignClient userFeignClient;

    public User getUserByEmail(String email){
        User user = userFeignClient.getUserByEmail(email).getBody();
        if(user == null){
            logger.error("Email not found: " + email);
            throw new IllegalArgumentException("Email not found. ");
        }
        logger.info("Email found: " + email);
        return user;
    }


}
