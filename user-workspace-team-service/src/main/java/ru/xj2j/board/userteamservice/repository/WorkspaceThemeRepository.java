package ru.xj2j.board.userteamservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.xj2j.board.userteamservice.entity.Workspace;
import ru.xj2j.board.userteamservice.entity.WorkspaceTheme;

import java.util.List;

@Repository
public interface WorkspaceThemeRepository extends JpaRepository<WorkspaceTheme, Long> {
    List<WorkspaceTheme> findByWorkspace(Workspace workspace);
}
