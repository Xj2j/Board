package ru.xj2j.projectboard.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.xj2j.projectboard.model.Team;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
