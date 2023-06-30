package ru.xj2j.board.userteamservice.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import ru.xj2j.board.userteamservice.DTO.WorkspaceDTO;
import ru.xj2j.board.userteamservice.DTO.WorkspaceMemberCreateDTO;
import ru.xj2j.board.userteamservice.DTO.WorkspaceMemberDTO;
import ru.xj2j.board.userteamservice.entity.Workspace;
import ru.xj2j.board.userteamservice.entity.WorkspaceMember;

@Component
public class WorkspaceMapper {

    private final ObjectMapper objectMapper;

    public WorkspaceMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public WorkspaceDTO toDto(Workspace workspace) {
        return objectMapper.convertValue(workspace, WorkspaceDTO.class);
    }

    public static WorkspaceMemberDTO toDto(WorkspaceMember entity) {
        WorkspaceMemberDTO dto = new WorkspaceMemberDTO();
        dto.setId(entity.getId());
        dto.getMember().setId(entity.getMember().getId());
        dto.setRole(entity.getRole());
        dto.getMember().setIsActive(entity.getMember().getIsActive());
        return dto;
    }

    public static WorkspaceMember toEntity(WorkspaceMemberCreateDTO dto) {
        WorkspaceMember entity = new WorkspaceMember();
        entity.setRole(dto.getRole());
        entity.getMember().setIsActive(dto.getMember().getIsActive());
        return entity;
    }

}
