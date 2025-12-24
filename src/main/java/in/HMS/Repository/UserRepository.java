package in.HMS.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import in.HMS.Entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
}
