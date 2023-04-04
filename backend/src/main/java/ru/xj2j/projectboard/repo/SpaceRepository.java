package ru.xj2j.projectboard.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.xj2j.projectboard.model.Space;

public interface SpaceRepository extends JpaRepository<Space, Long> {

}
