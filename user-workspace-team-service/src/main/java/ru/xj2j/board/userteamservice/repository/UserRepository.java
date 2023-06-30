package ru.xj2j.board.userteamservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.xj2j.board.userteamservice.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
