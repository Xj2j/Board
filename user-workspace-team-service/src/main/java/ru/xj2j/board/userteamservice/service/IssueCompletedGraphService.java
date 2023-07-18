package ru.xj2j.board.userteamservice.service;

import org.springframework.stereotype.Service;
import ru.xj2j.board.userteamservice.DTO.IssueCompletedGraphDTO;
import ru.xj2j.board.userteamservice.entity.Issue;
import ru.xj2j.board.userteamservice.entity.User;
import ru.xj2j.board.userteamservice.openfeign.IssueClient;

import java.util.ArrayList;
import java.util.List;

@Service
public class IssueCompletedGraphService {

    private final IssueClient issueClient;

    public IssueCompletedGraphService(IssueClient issueClient) {
        this.issueClient = issueClient;
    }

    public List<IssueCompletedGraphDTO> getUserIssueCompletedGraph(Long id, int month, User user) {
        List<IssueCompletedGraphDTO> issueGraphDTOs = issueClient.getCompletedIssues(id, month, user);

        return issueGraphDTOs;
    }
}
