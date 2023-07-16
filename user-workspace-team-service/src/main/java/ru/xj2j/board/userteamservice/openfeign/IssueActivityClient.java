package ru.xj2j.board.userteamservice.openfeign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.xj2j.board.userteamservice.entity.IssueActivity;

import java.time.LocalDate;
import java.util.List;

@FeignClient(name = "issue-activity-service")
public interface IssueActivityClient {

    @GetMapping("/api/issue-activities")
    List<IssueActivity> getIssueActivities(@RequestParam("workspaceId") Long workspaceId, @RequestParam("date") LocalDate date);
}
