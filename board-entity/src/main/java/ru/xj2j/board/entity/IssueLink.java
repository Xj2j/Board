package ru.xj2j.board.entity;

import lombok.Data;
import jakarta.persistence.*;

@Entity
@Table(name = "issue_links")
@Data
public class IssueLink {
    public enum Type {
        RELATES_TO("Relates To"),
        BLOCKED_BY("Blocked By"),
        DUPLICATE_OF("Duplicate Of");

        private final String label;

        Type(String label) {
            this.label = label;
        }

        @Override
        public String toString() {
            return label;
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Issue issue;

    @Enumerated(EnumType.STRING)
    private Type type;

    @ManyToOne(fetch = FetchType.LAZY)
    private Issue linkedIssue;
}
