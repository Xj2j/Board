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

    @Autowired
    private WorkspaceRepository workspaceRepository;

    @Autowired
    private WorkspaceMapper workSpaceMapper;

    public WorkspaceDTO getWorkspaceById(Long workspaceId) throws ValidationException {
        Optional<Workspace> workspace = workspaceRepository.findById(workspaceId);
        if (workspace == null) {
            throw new ValidationException("Workspace not found");
        }

        return workSpaceMapper.toDto(workspace.get());
    }

    public WorkspaceDTO create(WorkspaceDTO request, String owner) throws ValidationException {
        if (request.getName() == null || request.getName().isEmpty()) {
            throw new ValidationException("Workspace name is required");
        }

        Workspace workspace = new Workspace();
        workspace.setName(request.getName());
        //workSpace.setDescription(request.getDescription());
        workspace.setOwner(request.getOwner());
        workspaceRepository.save(workspace);

        return workSpaceMapper.toDto(workspace);
    }

    public Workspace updateWorkspace(Long workspaceId, WorkspaceDTO workSpaceDTO) throws WorkspaceNotFoundException {
        Optional<Workspace> optionalWorkSpace = workspaceRepository.findById(workspaceId);
        if (optionalWorkSpace.isPresent()) {
            Workspace workSpace = optionalWorkSpace.get();

            // Обновляем свойства workSpace согласно workSpaceDTO...

            workspaceRepository.save(workSpace);

            return workSpace;
        } else {
            throw new WorkspaceNotFoundException();
        }
    }

    public void deleteWorkspace(Long workspaceId) throws WorkspaceNotFoundException {
        Optional<Workspace> optionalWorkSpace = workspaceRepository.findById(workspaceId);
        if (optionalWorkSpace.isPresent()) {
            Workspace workSpace = optionalWorkSpace.get();
            workspaceRepository.delete(workSpace);
        } else {
            throw new WorkspaceNotFoundException();
        }
    }

    public List<WorkspaceDTO> getUserWorkspaces(String userEmail) throws ValidationException {
        List<Workspace> workSpaces = workspaceRepository.findByUserEmail(userEmail);
        List<WorkspaceDTO> workspaceDTOS = new ArrayList<>();
        for (Workspace workSpace : workSpaces) {
            workspaceDTOS.add(workSpaceMapper.toDto(workSpace));
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