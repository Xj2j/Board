package ru.xj2j.board.userteamservice.openfeign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.xj2j.board.userteamservice.DTO.ProjectMember;
import ru.xj2j.board.userteamservice.entity.User;
import java.util.List;

@FeignClient(name = "project-issue-service")
public interface ProjectMemberClient {
    @GetMapping("/projects/{lastWorkspaceId}")
    List<ProjectMember> getProjectMemberByWorkspaceIdAndMember(@PathVariable("lastWorkspaceId") Long lastWorkspaceId, User user);
}
