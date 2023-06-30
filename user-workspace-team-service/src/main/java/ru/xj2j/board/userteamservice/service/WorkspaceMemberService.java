package ru.xj2j.board.userteamservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.xj2j.board.userteamservice.DTO.WorkspaceMemberCreateDTO;
import ru.xj2j.board.userteamservice.DTO.WorkspaceMemberDTO;
import ru.xj2j.board.userteamservice.DTO.WorkspaceMemberUpdateDTO;
import ru.xj2j.board.userteamservice.entity.User;
import ru.xj2j.board.userteamservice.entity.Workspace;
import ru.xj2j.board.userteamservice.entity.WorkspaceMember;
import ru.xj2j.board.userteamservice.repository.UserRepository;
import ru.xj2j.board.userteamservice.repository.WorkspaceMemberRepository;
import ru.xj2j.board.userteamservice.repository.WorkspaceRepository;
import ru.xj2j.board.userteamservice.util.WorkspaceMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WorkspaceMemberService {

    @Autowired
    private WorkspaceMemberRepository workspaceMemberRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WorkspaceRepository workspaceRepository;

    //@Override
    public List<WorkspaceMemberDTO> getAllMembers(Long workspaceId) {
        //List<WorkspaceMember> members = workSpaceMemberRepository.findByWorkspaceIdAndMember(workspaceId);
        List<WorkspaceMember> members = workspaceMemberRepository.findByWorkspaceId(workspaceId);
        return members.stream().map(WorkspaceMapper::toDto).collect(Collectors.toList());
    }

    //@Override
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

        WorkspaceMember newMember = WorkspaceMapper.toEntity(memberDto);
        newMember.setWorkspace(workspace);
        newMember.setMember(userOpt.get());
        WorkspaceMember savedMember = workspaceMemberRepository.save(newMember);
        return WorkspaceMapper.toDto(savedMember);
    }

    //@Override
    public WorkspaceMemberDTO updateMember(Long workspaceId, Long memberId, WorkspaceMemberUpdateDTO memberDto, User user) {
        Optional<WorkspaceMember> memberOpt = workspaceMemberRepository.findByIdAndWorkspaceId(memberId, workspaceId);
        if (!memberOpt.isPresent()) {
            return null;
        }
        WorkspaceMember member = memberOpt.get();

        if (user.getId() == member.getMember().getId()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You cannot update your own role");
        }

        Optional<WorkspaceMember> requesterOpt = workspaceMemberRepository.findByWorkspaceIdAndMember(workspaceId, user);
        if (!requesterOpt.isPresent() || requesterOpt.get().getRole() < memberDto.getRole()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You cannot update a role that is higher than your own role");
        }

        member.setRole(memberDto.getRole());
        member.getMember().setIsActive(memberDto.getMember().getIsActive());
        WorkspaceMember updatedMember = workspaceMemberRepository.save(member);
        return WorkspaceMapper.toDto(updatedMember);
    }

    //@Override
    public boolean deleteMember(Long workspaceId, Long memberId, User user) {
        Optional<WorkspaceMember> memberOpt = workspaceMemberRepository.findByIdAndWorkspaceId(memberId, workspaceId);
        if (!memberOpt.isPresent()) {
            return false;
        }
        WorkspaceMember member = memberOpt.get();

        Optional<WorkspaceMember> requesterOpt = workspaceMemberRepository.findByWorkspaceIdAndMember(workspaceId, user);
        if (!requesterOpt.isPresent() || requesterOpt.get().getRole() < member.getRole()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You cannot delete a member with a higher role than your own");
        }

        workspaceMemberRepository.delete(member);
        return true;
    }
}

