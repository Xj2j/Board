package ru.xj2j.board.userteamservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.xj2j.board.userteamservice.repository.IssueActivityRepository;

import java.time.LocalDate;

@Service
public class IssueActivityService {

    @Autowired
    private IssueActivityRepository issueActivityRepository;

    public List<ActivityCount> getUserIssueActivities(String slug, String username) {
        LocalDate todayMinus3Months = LocalDate.now().minusMonths(3);

        return issueActivityRepository.getUserIssueActivities(
                slug, username, todayMinus3Months);
    }
}
