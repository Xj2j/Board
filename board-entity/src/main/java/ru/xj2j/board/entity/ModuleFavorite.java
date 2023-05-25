package ru.xj2j.board.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "module_favorites")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ModuleFavorite extends ProjectBaseModel {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "module_id")
    private Module module;

    @Override
    public String toString() {
        return String.format("%s <%s>", this.user.getEmail(), this.module.getName());
    }
}
