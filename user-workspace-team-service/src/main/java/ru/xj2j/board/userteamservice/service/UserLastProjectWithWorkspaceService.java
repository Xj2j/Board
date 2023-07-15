package ru.xj2j.board.userteamservice.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import ru.xj2j.board.userteamservice.DTO.ProjectMember;
import ru.xj2j.board.userteamservice.DTO.UserDTO;
import ru.xj2j.board.userteamservice.entity.User;
import ru.xj2j.board.userteamservice.entity.Workspace;
import ru.xj2j.board.userteamservice.exception.UserNotFoundException;
import ru.xj2j.board.userteamservice.openfeign.ProjectClient;
import ru.xj2j.board.userteamservice.openfeign.ProjectMemberClient;
import ru.xj2j.board.userteamservice.repository.UserRepository;
import ru.xj2j.board.userteamservice.repository.WorkspaceRepository;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserLastProjectWithWorkspaceService {
    private final UserRepository userRepository;
    private final WorkspaceRepository workspaceRepository;
    private final ProjectMemberClient projectMemberClient;

    private final ObjectMapper objectMapper;

    public UserLastProjectWithWorkspaceService(UserRepository userRepository,
                                               WorkspaceRepository workspaceRepository,
                                               ProjectMemberClient projectMemberClient, ObjectMapper objectMapper) {
        this.userRepository = userRepository;
        this.workspaceRepository = workspaceRepository;
        this.projectMemberClient = projectMemberClient;
        this.objectMapper = objectMapper;
    }

    public Map<String, Object> getLastProjectWithWorkspace(UserDTO userDTO) throws UserNotFoundException {
        User user = userRepository.findById(userDTO.getId())
                .orElseThrow(() -> new UserNotFoundException());

        Long lastWorkspaceId = user.getLastWorkspaceId();

        if (lastWorkspaceId == null) {
            return Collections.singletonMap("project_details", Collections.emptyList());
        }

        Workspace workspace= workspaceRepository.findById(lastWorkspaceId)
                .orElseThrow();

        List<ProjectMember> projectMembers = projectMemberClient.getProjectMemberByWorkspaceIdAndMember(lastWorkspaceId, user);

        List<Map<String, Object>> projectDetails = projectMembers.stream()
                .map(projectMember -> objectMapper.convertValue(projectMember, new TypeReference<Map<String, Object>>() {}))
                .collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("workspace_details", objectMapper.convertValue(workspace, new TypeReference<Map<String, Object>>() {}));
        response.put("project_details", projectDetails);

        return response;
    }
}