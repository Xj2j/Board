package ru.xj2j.board.board.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "view_favorites")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IssueViewFavorite extends ProjectBaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "view_id")
    private IssueView view;

    @Override
    public String toString() {
        return String.format("%s <%s>", user.getEmail(), view.getName());
    }
}
