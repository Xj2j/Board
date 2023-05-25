package ru.xj2j.board.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "issue_views")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IssueView extends ProjectBaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "query", columnDefinition = "json")
    private String query;

    @Column(name = "access")
    private Integer access;

    @Column(name = "query_data", columnDefinition = "json")
    private String queryData;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    @Override
    public String toString() {
        return String.format("%s <%s>", name, project.getName());
    }
}
