package ru.xj2j.board.userteamservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.xj2j.board.userteamservice.DTO.IssueActivityDTO;
import ru.xj2j.board.userteamservice.entity.IssueActivity;
import ru.xj2j.board.userteamservice.openfeign.IssueActivityClient;
import ru.xj2j.board.userteamservice.repository.IssueActivityRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class IssueActivityService {

    private final IssueActivityClient issueActivityClient;

    public IssueActivityService(IssueActivityClient issueActivityClient) {
        this.issueActivityClient = issueActivityClient;
    }

    public List<IssueActivityDTO> getUserActivityGraph(Long workspaceId) {
        LocalDate sixMonthsAgo = LocalDate.now().minusMonths(6);

        List<IssueActivity> issueActivities = issueActivityClient.getIssueActivities(workspaceId, sixMonthsAgo);

        List<IssueActivityDTO> issueActivityDTOs = new ArrayList<>();
        // Преобразование IssueActivity в IssueActivityDTO
        for (IssueActivity issueActivity : issueActivities) {
            IssueActivityDTO issueActivityDTO = new IssueActivityDTO(issueActivity.getCreatedDate(), issueActivity.getActivityCount());
            issueActivityDTOs.add(issueActivityDTO);
        }

        return issueActivityDTOs;
    }
}
