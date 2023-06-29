package ru.xj2j.board.userteamservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.xj2j.board.userteamservice.DTO.UserDto;
import ru.xj2j.board.userteamservice.DTO.WorkspaceDto;
import ru.xj2j.board.userteamservice.entity.User;
import ru.xj2j.board.userteamservice.entity.Workspace;
import ru.xj2j.board.userteamservice.repository.UserRepository;
import ru.xj2j.board.userteamservice.repository.WorkspaceRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WorkspaceRepository workspaceRepository;

    @Autowired
    private IssueActivityRepository issueActivityRepository;

    @Autowired
    private ObjectMapper objectMapper;

    public UserDto getCurrentUser() throws NotFoundException {
        User currentUser = userRepository.findById(getUserId()).orElseThrow(() -> new NotFoundException("User not found"));

        Workspace workspace = currentUser.getLastWorkspaceId() != null ? workspaceRepository.findById(currentUser.getLastWorkspaceId()).orElse(null) : null;
        String lastWorkspaceSlug = workspace != null ? workspace.getSlug() : null;

        Workspace fallbackWorkspace = workspaceRepository.findFirstByWorkspaceMembersMemberOrderByCreatedAtAsc(currentUser);
        String fallbackWorkspaceSlug = fallbackWorkspace != null ? fallbackWorkspace.getSlug() : null;

        int invitesCount = workspaceMemberInviteRepository.countByEmail(currentUser.getEmail());
        int assignedIssuesCount = issueRepository.countByAssigneesIn(Collections.singletonList(currentUser));

        UserDto userDto = objectMapper.convertValue(currentUser, UserDto.class);
        userDto.setWorkspace(new WorkspaceDto(currentUser.getLastWorkspaceId(), lastWorkspaceSlug, fallbackWorkspace.getId(), fallbackWorkspaceSlug, invitesCount));
        userDto.getIssues().setAssignedIssues(assignedIssuesCount);

        return userDto;
    }

    public void updateUserOnBoarded(boolean isOnboarded) throws NotFoundException {
        User currentUser = userRepository.findById(getUserId()).orElseThrow(() -> new NotFoundException("User not found"));
        currentUser.setIsOnboarded(isOnboarded);
        userRepository.save(currentUser);
    }


    /*public Page<IssueActivityDto> getUserActivity(Pageable pageable) {
        Page<IssueActivity> issueActivityPage = issueActivityRepository.findByActor(getUserId(), pageable);
        return issueActivityPage.map(issueActivity -> objectMapper.convertValue(issueActivity, IssueActivityDto.class));
    }

    private long getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return userDetails.getId();
    }*/
}
