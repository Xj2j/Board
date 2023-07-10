package ru.xj2j.board.userteamservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.xj2j.board.userteamservice.DTO.WorkspaceMemberCreateDTO;
import ru.xj2j.board.userteamservice.DTO.WorkspaceMemberDTO;
import ru.xj2j.board.userteamservice.entity.User;
import ru.xj2j.board.userteamservice.entity.Workspace;
import ru.xj2j.board.userteamservice.entity.WorkspaceMember;
import ru.xj2j.board.userteamservice.exception.WorkspaceNotFoundException;
import ru.xj2j.board.userteamservice.repository.UserRepository;
import ru.xj2j.board.userteamservice.repository.WorkspaceMemberRepository;
import ru.xj2j.board.userteamservice.repository.WorkspaceRepository;
import ru.xj2j.board.userteamservice.util.WorkspaceMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WorkspaceMemberService {

    private WorkspaceMemberRepository workspaceMemberRepository;

    private UserRepository userRepository;

    private WorkspaceRepository workspaceRepository;

    private WorkspaceMapper workspaceMapper;

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
        //List<WorkspaceMember> members = workSpaceMemberRepository.findByWorkspaceIdAndMember(workspaceId);
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

        WorkspaceMember newMember = workspaceMapper.toEntity(memberDto);  //WorkspaceMapper.toEntity(memberDto);
        newMember.setWorkspace(workspace);
        newMember.setMember(userOpt.get());
        WorkspaceMember savedMember = workspaceMemberRepository.save(newMember);
        return workspaceMapper.toDto(savedMember);
    }

    public WorkspaceMemberDTO updateMember(Long workspaceId, Long memberId, WorkspaceMemberDTO memberDto, User user) {
        Optional<WorkspaceMember> memberOpt = workspaceMemberRepository.findByIdAndWorkspaceId(memberId, workspaceId);
        if (!memberOpt.isPresent()) {
            return null;
        }
        WorkspaceMember member = memberOpt.get();

        if (user.getId() == member.getMember().getId()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You cannot update your own role");
        }

        Optional<WorkspaceMember> requesterOpt = workspaceMemberRepository.findByWorkspaceIdAndMember(workspaceId, user);
        if (!requesterOpt.isPresent() || requesterOpt.get().getRole().compareTo(member.getRole()) > 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You cannot update a role that is higher than your own role");
        }

        member.setRole(memberDto.getRole());
        member.getMember().setIsActive(memberDto.getMember().getIsActive());
        WorkspaceMember updatedMember = workspaceMemberRepository.save(member);
        return workspaceMapper.toDto(updatedMember);
    }

    public boolean deleteMember(Long workspaceId, Long memberId, User user) {
        Optional<WorkspaceMember> memberOpt = workspaceMemberRepository.findByIdAndWorkspaceId(memberId, workspaceId);
        if (!memberOpt.isPresent()) {
            return false;
        }
        WorkspaceMember member = memberOpt.get();

        Optional<WorkspaceMember> requesterOpt = workspaceMemberRepository.findByWorkspaceIdAndMember(workspaceId, user);
        if (!requesterOpt.isPresent() || requesterOpt.get().getRole().compareTo(member.getRole()) > 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You cannot delete a member with a higher role than your own");
        }

        workspaceMemberRepository.delete(member);
        return true;
    }
}

