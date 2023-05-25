package ru.xj2j.board.entity;

import jakarta.persistence.*;
import lombok.*;
@Entity(name = "CycleFavorite")
@Table(name = "cycle_favorites")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CycleFavorite extends ProjectBaseModel {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cycle_id", referencedColumnName = "id")
    private Cycle cycle;

    @Override
    public String toString() {
        return String.format("%s <%s>", this.getUser().getEmail(), this.getCycle().getName());
    }
}