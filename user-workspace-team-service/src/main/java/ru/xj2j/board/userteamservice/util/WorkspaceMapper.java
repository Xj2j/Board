package ru.xj2j.board.userteamservice.util;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.xj2j.board.userteamservice.DTO.*;
import ru.xj2j.board.userteamservice.entity.*;

@Component
public class WorkspaceMapper {

    /*private final ObjectMapper objectMapper;

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
    }*/

    private final ModelMapper modelMapper;

    @Autowired
    public WorkspaceMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;

        // Настройка маппинга для поля owner
        modelMapper.addMappings(new PropertyMap<WorkspaceDTO, Workspace>() {
            @Override
            protected void configure() {
                map().setOwner(toEntity(source.getOwner()));
            }
        });

        modelMapper.addMappings(new PropertyMap<Workspace, WorkspaceDTO>() {
            @Override
            protected void configure() {
                map().setOwner(toDto(source.getOwner()));
            }
        });

        modelMapper.addMappings(new PropertyMap<WorkspaceMemberInviteDTO, WorkspaceMemberInvite>() {
            @Override
            protected void configure() {
                map().setWorkspace(toEntity(source.getWorkspace()));
            }
        });

        modelMapper.addMappings(new PropertyMap<WorkspaceMemberInvite, WorkspaceMemberInviteDTO>() {
            @Override
            protected void configure() {
                map().setWorkspace(toDto(source.getWorkspace()));
            }
        });

        modelMapper.addMappings(new PropertyMap<TeamMemberDTO, TeamMember>() {
            @Override
            protected void configure() {
                map().setWorkspace(toEntity(source.getWorkspace()));
            }
        });

        modelMapper.addMappings(new PropertyMap<TeamMember, TeamMemberDTO>() {
            @Override
            protected void configure() {
                map().setWorkspace(toDto(source.getWorkspace()));
            }
        });

        modelMapper.addMappings(new PropertyMap<TeamMemberDTO, TeamMember>() {
            @Override
            protected void configure() {
                map().setMember(toEntity(source.getMember()));
            }
        });

        modelMapper.addMappings(new PropertyMap<TeamMember, TeamMemberDTO>() {
            @Override
            protected void configure() {
                map().setMember(toDto(source.getMember()));
            }
        });

        modelMapper.addMappings(new PropertyMap<TeamMemberDTO, TeamMember>() {
            @Override
            protected void configure() {
                map().setTeam(toEntity(source.getTeam()));
            }
        });

        modelMapper.addMappings(new PropertyMap<TeamMember, TeamMemberDTO>() {
            @Override
            protected void configure() {
                map().setTeam(toDto(source.getTeam()));
            }
        });

        modelMapper.addMappings(new PropertyMap<TeamDTO, Team>() {
            @Override
            protected void configure() {
                map().setWorkspace(toEntity(source.getWorkspace()));
            }
        });

        modelMapper.addMappings(new PropertyMap<Team, TeamDTO>() {
            @Override
            protected void configure() {
                map().setWorkspace(toDto(source.getWorkspace()));
            }
        });

    }

    public Workspace toEntity(WorkspaceDTO workspaceDTO) {
        return modelMapper.map(workspaceDTO, Workspace.class);
    }

    public WorkspaceDTO toDto(Workspace workspace) {
        return modelMapper.map(workspace, WorkspaceDTO.class);
    }

    public WorkspaceMember toEntity(WorkspaceMemberCreateDTO workspaceMemberDTO) {
        return modelMapper.map(workspaceMemberDTO, WorkspaceMember.class);
    }

    public WorkspaceMemberDTO toDto(WorkspaceMember workspaceMember) {
        return modelMapper.map(workspaceMember, WorkspaceMemberDTO.class);
    }

    public WorkspaceMember toEntity(WorkspaceMemberDTO workspaceMemberDTO) {
        return modelMapper.map(workspaceMemberDTO, WorkspaceMember.class);
    }

    public User toEntity(UserDTO userDto) {
        return modelMapper.map(userDto, User.class);
    }

    public UserDTO toDto(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    public WorkspaceMemberInvite toEntity(WorkspaceMemberInviteDTO workspaceMemberInviteDTO) {
        return modelMapper.map(workspaceMemberInviteDTO, WorkspaceMemberInvite.class);
    }

    public WorkspaceMemberInviteDTO toDto(WorkspaceMemberInvite workspaceMemberInvite) {
        return modelMapper.map(workspaceMemberInvite, WorkspaceMemberInviteDTO.class);
    }

    public TeamMember toEntity(TeamMemberDTO teamMemberDTO) {
        return modelMapper.map(teamMemberDTO, TeamMember.class);
    }

    public TeamMemberDTO toDto(TeamMember teamMember) {
        return modelMapper.map(teamMember, TeamMemberDTO.class);
    }

    public Team toEntity(TeamDTO teamDTO) {
        return modelMapper.map(teamDTO, Team.class);
    }

    public TeamDTO toDto(Team team) {
        return modelMapper.map(team, TeamDTO.class);
    }

}
