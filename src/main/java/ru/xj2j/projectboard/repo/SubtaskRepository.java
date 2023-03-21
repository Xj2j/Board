package ru.xj2j.projectboard.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.xj2j.projectboard.model.Subtask;

public interface SubtaskRepository extends JpaRepository<Subtask, Long> {
}
