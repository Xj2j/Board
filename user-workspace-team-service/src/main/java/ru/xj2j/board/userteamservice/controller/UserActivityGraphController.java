package ru.xj2j.board.userteamservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.xj2j.board.userteamservice.DTO.IssueActivityDTO;
import ru.xj2j.board.userteamservice.service.IssueActivityService;

import java.util.List;

@RestController
@RequestMapping("users/me/workspaces/id/activity-graph/")
public class UserActivityGraphController {

    private final IssueActivityService issueActivityService;

    public UserActivityGraphController(IssueActivityService issueActivityService) {
        this.issueActivityService = issueActivityService;
    }

    @GetMapping
    public ResponseEntity<List<IssueActivityDTO>> getUserActivityGraph(@PathVariable("id") Long id) {

            List<IssueActivityDTO> issueActivities = issueActivityService.getUserActivityGraph(id);
            return ResponseEntity.ok(issueActivities);
    }

}