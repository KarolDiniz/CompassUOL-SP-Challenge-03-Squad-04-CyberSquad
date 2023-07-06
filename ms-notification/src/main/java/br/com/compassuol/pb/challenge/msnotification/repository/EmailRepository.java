package br.com.compassuol.pb.challenge.msnotification.repository;
import br.com.compassuol.pb.challenge.msnotification.entities.Email;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EmailRepository extends JpaRepository<Email, UUID> {
}