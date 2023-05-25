package ru.xj2j.board.entity;

import lombok.Data;
import jakarta.persistence.*;

@Entity
@Table(name = "issue_blockers")
@Data
public class IssueBlocker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Issue issue;

    @ManyToOne(fetch = FetchType.LAZY)
    private Issue blockerIssue;
}
