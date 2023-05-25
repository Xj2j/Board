package ru.xj2j.board.entity;

import lombok.Data;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "issue_attachments")
@Data
public class IssueAttachment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Issue issue;

    private String fileName;

    private String fileType;

    @Column(columnDefinition = "bytea")
    private byte[] data;

    @Column(nullable = false)
    private LocalDateTime createdAt;
}
