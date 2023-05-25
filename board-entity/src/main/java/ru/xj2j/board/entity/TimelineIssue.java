package ru.xj2j.board.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

import java.util.HashMap;
import java.util.Map;


@Entity
@Table(name = "issue_timelines")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TimelineIssue extends ProjectBaseModel {

    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "issue_id", nullable = false)
    private Issue issue;

    @Column(name = "sequence_id", nullable = false)
    private Double sequenceId = 1.0;

    @Column(name = "links", columnDefinition = "jsonb not null default '{}'")
    @Convert(converter = JsonConverter.class)
    private Map<String, String> links = new HashMap<>();

    @Override
    public String toString() {
        return String.valueOf(issue);
    }
}
