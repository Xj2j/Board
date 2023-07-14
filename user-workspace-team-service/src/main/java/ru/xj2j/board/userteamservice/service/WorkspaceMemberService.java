package ru.xj2j.board.userteamservice.service;

import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.xj2j.board.userteamservice.DTO.WorkspaceMemberCreateDTO;
import ru.xj2j.board.userteamservice.DTO.WorkspaceMemberDTO;
import ru.xj2j.board.userteamservice.JsonConverter;
import ru.xj2j.board.userteamservice.entity.User;
import ru.xj2j.board.userteamservice.entity.Workspace;
import ru.xj2j.board.userteamservice.entity.WorkspaceMember;
import ru.xj2j.board.userteamservice.exception.ForbiddenException;
import ru.xj2j.board.userteamservice.exception.InviteWorkspaceNotFoundException;
import ru.xj2j.board.userteamservice.exception.WorkspaceMemberNotFoundException;
import ru.xj2j.board.userteamservice.exception.WorkspaceNotFoundException;
import ru.xj2j.board.userteamservice.repository.UserRepository;
import ru.xj2j.board.userteamservice.repository.WorkspaceMemberRepository;
import ru.xj2j.board.userteamservice.repository.WorkspaceRepository;
import ru.xj2j.board.userteamservice.util.WorkspaceMapper;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WorkspaceMemberService {

    private WorkspaceMemberRepository workspaceMemberRepository;

    private UserRepository userRepository;

    private WorkspaceRepository workspaceRepository;

    private WorkspaceMapper workspaceMapper;

    private JsonConverter jsonConverter;

    @Autowired
    public WorkspaceMemberService(WorkspaceMemberRepository workspaceMemberRepository, UserRepository userRepository, WorkspaceRepository workspaceRepository, WorkspaceMapper workspaceMapper) {
        this.workspaceMemberRepository = workspaceMemberRepository;
        this.userRepository = userRepository;
        this.workspaceRepository = workspaceRepository;
        this.workspaceMapper = workspaceMapper;
    }

    public WorkspaceMember getWorkspaceMemberByWorkspaceIdAndMemberEmail(Long workspaceId, String email) throws WorkspaceNotFoundException {
        return workspaceMemberRepository.findByWorkspaceIdAndMemberEmail(workspaceId, email)
                .orElseThrow(() -> new WorkspaceNotFoundException("Workspace member not found with id: " + workspaceId + " and email: " + email));
    }

    public List<WorkspaceMember> getWorkspaceMembersByWorkspaceIdAndMemberEmails(Long workspaceId, List<String> emails) {
        return workspaceMemberRepository.findByWorkspaceIdAndMemberEmailIn(workspaceId, emails);
    }

    public List<WorkspaceMemberDTO> getAllMembers(Long workspaceId) {
        List<WorkspaceMember> members = workspaceMemberRepository.findByWorkspaceId(workspaceId);
        return members.stream().map(workspaceMapper::toDto).collect(Collectors.toList());
    }

    public WorkspaceMemberDTO addMember(Long workspaceId, WorkspaceMemberCreateDTO memberDto) {
        Optional<User> userOpt = userRepository.findById(memberDto.getMember().getId());
        if (!userOpt.isPresent()) {
            return null;
        }

        Optional<Workspace> workspaceOpt = workspaceRepository.findById(workspaceId);
        if (!workspaceOpt.isPresent()) {
            return null;
        }

        Workspace workspace = workspaceOpt.get();

        WorkspaceMember newMember = workspaceMapper.toEntity(memberDto);
        newMember.setWorkspace(workspace);
        newMember.setMember(userOpt.get());
        WorkspaceMember savedMember = workspaceMemberRepository.save(newMember);
        return workspaceMapper.toDto(savedMember);
    }

    public WorkspaceMemberDTO updateMember(Long workspaceId, Long memberId, WorkspaceMemberDTO memberDto, User user) {
        Optional<WorkspaceMember> memberOpt = workspaceMemberRepository.findByWorkspaceIdAndId(memberId, workspaceId);
        if (!memberOpt.isPresent()) {
            return null;
        }
        WorkspaceMember member = memberOpt.get();

        if (user.getId() == member.getMember().getId()) {
            throw new BadRequestException("You cannot update your own role");
        }

        Optional<WorkspaceMember> requesterOpt = workspaceMemberRepository.findByWorkspaceIdAndMember(workspaceId, user);
        if (!requesterOpt.isPresent() || requesterOpt.get().getRole().compareTo(member.getRole()) > 0) {
            throw new BadRequestException("You cannot update a role that is higher than your own role");
        }

        member.setRole(memberDto.getRole());
        member.getMember().setIsActive(memberDto.getMember().getIsActive());
        WorkspaceMember updatedMember = workspaceMemberRepository.save(member);
        return workspaceMapper.toDto(updatedMember);
    }

    public WorkspaceMemberDTO updateWorkspaceMemberRole(Long workspaceId, Long memberId, WorkspaceMemberDTO workspaceMemberDTO) throws InviteWorkspaceNotFoundException {
        Optional<WorkspaceMember> existingMemberOptional = workspaceMemberRepository.findByWorkspaceIdAndId(memberId, workspaceId);
        WorkspaceMember existingMember = existingMemberOptional.orElseThrow(() -> new WorkspaceMemberNotFoundException("Workspace Member does not exist"));

        if (existingMember.getRole().compareTo(workspaceMemberDTO.getRole()) < 0) {
            throw new BadRequestException("You cannot update the role of a user with a higher role than yours");
        }

        // Delete related ProjectMembers
        List<ProjectMember> projectMembers = projectMemberRepository.findByWorkspaceMember(existingMember);
        projectMemberRepository.deleteAll(projectMembers);

        // Delete related ProjectFavorites
        List<ProjectFavorite> projectFavorites = projectFavoriteRepository.findByWorkspaceMember(existingMember);
        projectFavoriteRepository.deleteAll(projectFavorites);

        existingMember.setRole(workspaceMemberDTO.getRole());
        return workspaceMapper.toDto(workspaceMemberRepository.save(existingMember));
    }

    /*public boolean deleteMember(Long workspaceId, Long memberId, User user) {
        Optional<WorkspaceMember> memberOpt = workspaceMemberRepository.findByIdAndWorkspaceId(memberId, workspaceId);
        if (!memberOpt.isPresent()) {
            return false;
        }
        WorkspaceMember member = memberOpt.get();

        Optional<WorkspaceMember> requesterOpt = workspaceMemberRepository.findByWorkspaceIdAndMember(workspaceId, user);
        if (!requesterOpt.isPresent() || requesterOpt.get().getRole().compareTo(member.getRole()) > 0) {
            throw new BadRequestException("You cannot delete a member with a higher role than your own");
        }

        workspaceMemberRepository.delete(member);
        return true;
    }*/

    public void deleteWorkspaceMember(Long workspaceId, Long id, User requestingUser) {
        WorkspaceMember workspaceMember = workspaceMemberRepository.findByWorkspaceIdAndId(id, workspaceId)
                .orElseThrow(() -> new WorkspaceMemberNotFoundException("User role who is deleting the user does not exist"));

        WorkspaceMember requestingWorkspaceMember = workspaceMemberRepository.findByWorkspaceIdAndMember(workspaceId, requestingUser)
                .orElseThrow(() -> new WorkspaceMemberNotFoundException("Requesting user role does not exist"));

        if (requestingWorkspaceMember.getRole().compareTo(workspaceMember.getRole()) < 0) {
            throw new BadRequestException("You cannot delete a role that is higher than your own role");
        }

        projectMemberRepository.deleteByWorkspaceSlugAndMember(workspaceId, workspaceMember.getMember());
        projectFavoriteRepository.deleteByWorkspaceSlugAndUser(workspaceId, workspaceMember.getMember());
        cycleFavoriteRepository.deleteByWorkspaceSlugAndUser(workspaceId, workspaceMember.getMember());
        moduleFavoriteRepository.deleteByWorkspaceSlugAndUser(workspaceId, workspaceMember.getMember());
        pageFavoriteRepository.deleteByWorkspaceSlugAndUser(workspaceId, workspaceMember.getMember());
        issueViewFavoriteRepository.deleteByWorkspaceSlugAndUser(workspaceId, workspaceMember.getMember());
        issueAssigneeRepository.deleteByWorkspaceSlugAndAssignee(workspaceId, workspaceMember.getMember());
        moduleMemberRepository.deleteByWorkspaceSlugAndMember(workspaceId, workspaceMember.getMember());
        pageRepository.deleteByWorkspaceSlugAndOwnedBy(workspaceId, workspaceMember.getMember());

        workspaceMemberRepository.delete(workspaceMember);
    }

    public WorkspaceMemberDTO findByWorkspaceIdAndMember(Long workspaceId, User user) {
        return workspaceMapper.toDto(workspaceMemberRepository.findByWorkspaceIdAndMember(workspaceId, user)
                .orElseThrow(() -> new ForbiddenException("User not a member of workspace")));
    }

    public void updateViewProps(WorkspaceMemberDTO member, Map<String, Object> viewProps) {
        member.setViewProps(jsonConverter.convertViewPropsToString(viewProps));
        workspaceMemberRepository.save(workspaceMapper.toEntity(member));
    }
}

