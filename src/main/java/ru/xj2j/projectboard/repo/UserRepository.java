package ru.xj2j.projectboard.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.xj2j.projectboard.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Boolean existsByEmail(String email);
}
