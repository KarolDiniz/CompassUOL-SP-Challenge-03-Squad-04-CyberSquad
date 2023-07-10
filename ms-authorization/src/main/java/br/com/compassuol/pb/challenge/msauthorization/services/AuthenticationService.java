package br.com.compassuol.pb.challenge.msauthorization.services;

import br.com.compassuol.pb.challenge.msauthorization.entities.Role;
import br.com.compassuol.pb.challenge.msauthorization.entities.RoleName;
import br.com.compassuol.pb.challenge.msauthorization.entities.User;
import br.com.compassuol.pb.challenge.msauthorization.feignClients.UserFeignClient;
import br.com.compassuol.pb.challenge.msauthorization.response.TokenResponse;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Base64;
import java.util.List;

@Service
public class AuthenticationService {

    private static Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    //@Autowired
    private UserFeignClient userFeignClient;

    public TokenResponse generateToken(String email) {
        User user = userFeignClient.getUserByEmail(email).getBody();
        if (user == null) {
            logger.error("Email not found: " + email);
            throw new IllegalArgumentException("Email not found.");
        }

        List<Role> roles = user.getRoles();
        String role = roles.get(0).getName().name();
        String token;

        if (role.equals(RoleName.ROLE_ADMIN.toString())) {
            token = generateAdminToken();
        } else if (role.equals(RoleName.ROLE_OPERATOR.toString())) {
            token = generateOperatorToken();
        } else {
            logger.error("Invalid role: " + role);
            throw new IllegalArgumentException("Invalid role.");
        }

        logger.info("Token generated for email: " + email);
        return new br.com.compassuol.pb.challenge.msauthorization.response.TokenResponse(token);
    }

    private String generateAdminToken() {
        String token = "ROLE_ADMIN_TOKEN";
        return encodeToken(token);
    }

    private String generateOperatorToken() {
        String token = "ROLE_OPERATOR_TOKEN";
        return encodeToken(token);
    }

    private String encodeToken(String token) {
        byte[] encodedBytes = Base64.getEncoder().encode(token.getBytes());
        return new String(encodedBytes);
    }
}
