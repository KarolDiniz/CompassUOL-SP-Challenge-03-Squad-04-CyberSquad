package br.com.compassuol.pb.challenge.msuser.repository;
import br.com.compassuol.pb.challenge.msuser.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
