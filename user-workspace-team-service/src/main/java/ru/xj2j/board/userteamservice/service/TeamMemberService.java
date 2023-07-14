package ru.xj2j.board.userteamservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.xj2j.board.userteamservice.DTO.*;
import ru.xj2j.board.userteamservice.entity.Team;
import ru.xj2j.board.userteamservice.entity.TeamMember;
import ru.xj2j.board.userteamservice.entity.User;
import ru.xj2j.board.userteamservice.entity.Workspace;
import ru.xj2j.board.userteamservice.exception.TeamAlreadyExistsException;
import ru.xj2j.board.userteamservice.exception.WorkspaceNotFoundException;
import ru.xj2j.board.userteamservice.repository.TeamMemberRepository;
import ru.xj2j.board.userteamservice.repository.UserRepository;
import ru.xj2j.board.userteamservice.repository.WorkspaceRepository;
import ru.xj2j.board.userteamservice.util.WorkspaceMapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TeamMemberService {
    private final TeamMemberRepository teamMemberRepository;
    private final WorkspaceService workspaceService;
    private final UserRepository userRepository;
    private final WorkspaceMapper workspaceMapper;

    @Autowired
    public TeamMemberService(TeamMemberRepository teamMemberRepository, WorkspaceService workspaceService, UserRepository userRepository, WorkspaceMapper workspaceMapper) {
        this.teamMemberRepository = teamMemberRepository;
        this.workspaceService = workspaceService;
        this.userRepository = userRepository;
        this.workspaceMapper = workspaceMapper;
    }

    public Set<TeamMember> getTeamMembersByWorkspaceId(Long workspaceId) {
        return teamMemberRepository.getTeamMembersByWorkspaceId(workspaceId);
    }

    public TeamMemberDTO createTeamMember(Long workspaceId, CreateTeamRequest request) throws TeamAlreadyExistsException, WorkspaceNotFoundException {
        Workspace workspace = workspaceMapper.toEntity(workspaceService.getWorkspaceById(workspaceId));

        validateMembersExistence(workspace, request.getMembers());

        return workspaceMapper.toDto(saveTeamMember(workspace, request.getMembers(), workspaceMapper.toEntity(request.getTeam())));

        //return new TeamMemberDTO(createdTeamMember);
    }

    private void validateMembersExistence(Workspace workspace, Set<Long> members) throws TeamAlreadyExistsException {
        Set<Long> existingMemberIds = teamMemberRepository.findMemberIdsByWorkspace(workspace);
        Set<Long> nonExistingMembers = members.stream()
                .filter(memberId -> !existingMemberIds.contains(memberId))
                .collect(Collectors.toSet());

        if (!nonExistingMembers.isEmpty()) {
            Set<User> users = userRepository.findByIdIn(nonExistingMembers);
            Set<UserDTO> userDtoSet = users.stream()
                    .map(user -> new UserDTO(user.getId(), user.getEmail()))
                    .collect(Collectors.toSet());

            throw new TeamAlreadyExistsException(
                    String.format("%d of the member(s) are not a part of the workspace", nonExistingMembers.size()),
                    userDtoSet
            );
        }
    }

    private TeamMember saveTeamMember(Workspace workspace, Set<Long> memberIds, Team team) throws TeamAlreadyExistsException {

        team.setWorkspace(workspace);
        team.setMembers(userRepository.findByIdIn(memberIds));

        if (teamMemberRepository.existsByWorkspaceAndTeam(workspace, team)) {
            throw new TeamAlreadyExistsException("The team with the name already exists");
        }

        return teamRepository.save(team);
    }
}
