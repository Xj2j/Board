package ru.xj2j.board.userteamservice.service;

import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.xj2j.board.userteamservice.DTO.WorkspaceDto;
import ru.xj2j.board.userteamservice.entity.Workspace;
import ru.xj2j.board.userteamservice.repository.WorkspaceRepository;
import ru.xj2j.board.userteamservice.util.WorkspaceMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class WorkspaceService {

    @Autowired
    private WorkspaceRepository workSpaceRepository;

    @Autowired
    private WorkspaceMapper workSpaceMapper;

    public WorkspaceDto getWorkspaceById(Long workspaceId) throws ValidationException {
        Optional<Workspace> workspace = workSpaceRepository.findById(workspaceId);
        if (workspace == null) {
            throw new ValidationException("Workspace not found");
        }

        return workSpaceMapper.toDto(workspace.get());
    }

    public WorkspaceDto create(WorkspaceDto request, String owner) throws ValidationException {
        if (request.getName() == null || request.getName().isEmpty()) {
            throw new ValidationException("Workspace name is required");
        }

        Workspace workSpace = new Workspace();
        workSpace.setName(request.getName());
        //workSpace.setDescription(request.getDescription());
        workSpace.setOwner(request.getOwner());
        workSpaceRepository.save(workSpace);

        return workSpaceMapper.toDto(workSpace);
    }

    public List<WorkspaceDto> getUserWorkSpaces(String userEmail) throws ValidationException {
        List<Workspace> workSpaces = workSpaceRepository.findByUserEmail(userEmail);
        List<WorkspaceDto> workspaceDtos = new ArrayList<>();
        for (Workspace workSpace : workSpaces) {
            workspaceDtos.add(workSpaceMapper.toDto(workSpace));
        }

        return workspaceDtos;
    }
}