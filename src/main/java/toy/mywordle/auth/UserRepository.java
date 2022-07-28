package toy.mywordle.auth;

import org.springframework.data.jpa.repository.JpaRepository;
import toy.mywordle.domain.user;

public interface UserRepository extends JpaRepository<user, String> {
    public user findByUsername(String username);
}
