package ru.xj2j.board.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import org.jsoup.Jsoup;

import java.util.*;

@Entity
@Table(name = "issue_comments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class IssueComment extends ProjectBaseModel {

    @Column(name = "comment_stripped", nullable = false)
    private String commentStripped = "";

    @Column(name = "comment_json", columnDefinition = "jsonb not null default '{}'")
    @Convert(converter = JsonConverter.class)
    private Map<String, Object> commentJson = new HashMap<>();

    @Column(name = "comment_html", nullable = false)
    private String commentHtml = "<p></p>";

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "issue_comment_attachments", joinColumns = @JoinColumn(name = "comment_id"))
    @Column(name = "attachment_url", nullable = false)
    private List<String> attachments = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "issue_id", nullable = false)
    private Issue issue;

    @ManyToOne(fetch = FetchType.LAZY, optional = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "actor_id", nullable = true)
    private User actor;

    @Override
    public String toString() {
        return String.valueOf(issue);
    }

    @PrePersist
    void prePersist() {
        //commentStripped = StringUtils.stripToEmpty(Jsoup.parse(commentHtml).text());
        String str = Jsoup.parse(commentHtml).text();
        commentStripped = str == null ? "" : str.trim();
    }
}
