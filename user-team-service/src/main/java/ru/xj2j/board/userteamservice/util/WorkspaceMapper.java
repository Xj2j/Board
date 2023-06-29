package ru.xj2j.board.userteamservice.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import ru.xj2j.board.userteamservice.DTO.WorkspaceDto;
import ru.xj2j.board.userteamservice.DTO.WorkspaceMemberCreateDto;
import ru.xj2j.board.userteamservice.DTO.WorkspaceMemberDto;
import ru.xj2j.board.userteamservice.entity.Workspace;
import ru.xj2j.board.userteamservice.entity.WorkspaceMember;

import java.util.Optional;

@Component
public class WorkspaceMapper {

    private final ObjectMapper objectMapper;

    public WorkspaceMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public WorkspaceDto toDto(Workspace workspace) {
        return objectMapper.convertValue(workspace, WorkspaceDto.class);
    }

    public static WorkspaceMemberDto toDto(WorkspaceMember entity) {
        WorkspaceMemberDto dto = new WorkspaceMemberDto();
        dto.setId(entity.getId());
        dto.getMember().setId(entity.getMember().getId());
        dto.setRole(entity.getRole());
        dto.getMember().setIsActive(entity.getMember().getIsActive());
        return dto;
    }

    public static WorkspaceMember toEntity(WorkspaceMemberCreateDto dto) {
        WorkspaceMember entity = new WorkspaceMember();
        entity.setRole(dto.getRole());
        entity.getMember().setIsActive(dto.getMember().getIsActive());
        return entity;
    }

}
