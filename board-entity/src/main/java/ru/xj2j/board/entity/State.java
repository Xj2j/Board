package ru.xj2j.board.entity;

import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@Entity
@Table(name = "states")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "project")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class State {

    public enum Group {
        BACKLOG,
        UNSTARTED,
        STARTED,
        COMPLETED,
        CANCELLED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "color")
    private String color;

    @Column(name = "slug")
    private String slug;

    @Column(name = "sequence")
    private double sequence = 65535.0;

    @Enumerated(EnumType.STRING)
    @Column(name = "group_name", length = 20)
    private Group group = Group.BACKLOG;

    @Column(name = "is_default")
    private boolean isDefault;

    @ManyToOne
    private Project project;

}