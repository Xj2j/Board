package ru.xj2j.board.userteamservice.service;

import org.springframework.stereotype.Service;
import ru.xj2j.board.userteamservice.entity.User;
import ru.xj2j.board.userteamservice.entity.Workspace;
import ru.xj2j.board.userteamservice.entity.WorkspaceTheme;
import ru.xj2j.board.userteamservice.exception.WorkspaceNotFoundException;
import ru.xj2j.board.userteamservice.repository.WorkspaceRepository;
import ru.xj2j.board.userteamservice.repository.WorkspaceThemeRepository;

import java.util.List;

@Service
public class WorkspaceThemeService {

    private WorkspaceThemeRepository workspaceThemeRepository;

    private WorkspaceRepository workspaceRepository;

    public WorkspaceThemeService(WorkspaceThemeRepository workspaceThemeRepository, WorkspaceRepository workspaceRepository) {
        this.workspaceThemeRepository = workspaceThemeRepository;
        this.workspaceRepository = workspaceRepository;
    }

    public List<WorkspaceTheme> getAllWorkspaceThemesById(Long id) throws WorkspaceNotFoundException {
        Workspace workspace = workspaceRepository.findById(id)
                .orElseThrow(() -> new WorkspaceNotFoundException("Workspace not found"));
        return workspaceThemeRepository.findByWorkspace(workspace);
    }

    public WorkspaceTheme createWorkspaceTheme(Long id, WorkspaceTheme workspaceTheme, User actor) throws WorkspaceNotFoundException {
        Workspace workspace = workspaceRepository.findById(id)
                .orElseThrow(() -> new WorkspaceNotFoundException("Workspace not found"));
        workspaceTheme.setWorkspace(workspace);
        workspaceTheme.setActor(actor);
        return workspaceThemeRepository.save(workspaceTheme);
    }
}
