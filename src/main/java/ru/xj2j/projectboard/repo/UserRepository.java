package ru.xj2j.projectboard.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.xj2j.projectboard.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
