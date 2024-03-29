package ru.xj2j.board.userteamservice.service;

import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.xj2j.board.userteamservice.DTO.WorkspaceDTO;
import ru.xj2j.board.userteamservice.entity.Workspace;
import ru.xj2j.board.userteamservice.exception.WorkspaceNotFoundException;
import ru.xj2j.board.userteamservice.repository.WorkspaceRepository;
import ru.xj2j.board.userteamservice.util.WorkspaceMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class WorkspaceService {

    private WorkspaceRepository workspaceRepository;

    private WorkspaceMapper workspaceMapper;

    @Autowired
    public WorkspaceService(WorkspaceRepository workspaceRepository, WorkspaceMapper workspaceMapper) {
        this.workspaceRepository = workspaceRepository;
        this.workspaceMapper = workspaceMapper;
    }

    public WorkspaceDTO getWorkspaceById(Long workspaceId) throws WorkspaceNotFoundException {
        Optional<Workspace> workspace = workspaceRepository.findById(workspaceId);
        if (workspace == null) {
            throw new WorkspaceNotFoundException("Workspace not found");
        }

        return workspaceMapper.toDto(workspace.get());
    }

    public WorkspaceDTO create(WorkspaceDTO request, String owner) throws ValidationException {
        if (request.getName() == null || request.getName().isEmpty()) {
            throw new ValidationException("Workspace name is required");
        }

        Workspace workspace = new Workspace();
        workspace.setName(request.getName());
        workspace.setOwner(workspaceMapper.toEntity(request.getOwner()));
        workspaceRepository.save(workspace);

        return workspaceMapper.toDto(workspace);
    }

    /*public WorkspaceDTO updateWorkspace(Long workspaceId, WorkspaceDTO workSpaceDTO) throws WorkspaceNotFoundException {
        Optional<Workspace> optionalWorkSpace = workspaceRepository.findById(workspaceId);
        if (optionalWorkSpace.isPresent()) {
            Workspace workspace = optionalWorkSpace.get();

            workspace.se

            // Обновляем свойства workSpace согласно workSpaceDTO...

            workspaceRepository.save(workspace);

            return workSpaceMapper.toDto(workspace);
        } else {
            throw new WorkspaceNotFoundException("Workspace member not found with id: " + workspaceId);
        }
    }*/

    public void deleteWorkspace(Long workspaceId) throws WorkspaceNotFoundException {
        Optional<Workspace> optionalWorkSpace = workspaceRepository.findById(workspaceId);
        if (optionalWorkSpace.isPresent()) {
            Workspace workSpace = optionalWorkSpace.get();
            workspaceRepository.delete(workSpace);
        } else {
            throw new WorkspaceNotFoundException("Workspace member not found with id: " + workspaceId);
        }
    }

    public List<WorkspaceDTO> getUserWorkspaces(String userEmail) throws ValidationException {
        List<Workspace> workSpaces = workspaceRepository.findByUserEmail(userEmail);
        List<WorkspaceDTO> workspaceDTOS = new ArrayList<>();
        for (Workspace workSpace : workSpaces) {
            workspaceDTOS.add(workspaceMapper.toDto(workSpace));
        }

        return workspaceDTOS;
    }

    /*public List<Workspace> getUserWorkspaces(String ownerUsername) {
        return workspaceRepository.findByOwner(ownerUsername);
    }*/

    public boolean isWorkspaceAvailable(Long id) {
        return !workspaceRepository.existsById(id);
    }
}