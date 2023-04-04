package ru.xj2j.projectboard.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.xj2j.projectboard.model.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {

}
