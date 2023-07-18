package ru.xj2j.board.userteamservice.openfeign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.xj2j.board.userteamservice.DTO.IssueCompletedGraphDTO;
import ru.xj2j.board.userteamservice.entity.User;

import java.util.List;

@FeignClient(name = "project-issue-service")
public interface IssueClient {

    @GetMapping("/api/issues/completed")
    List<IssueCompletedGraphDTO> getCompletedIssues(@RequestParam("id") Long id, @RequestParam("month") int month, User user);
}
