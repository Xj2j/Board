package ru.xj2j.board.userteamservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "issue_activities")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IssueActivity extends ProjectBaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    private LocalDate createdDate;

    private int activityCount;
}