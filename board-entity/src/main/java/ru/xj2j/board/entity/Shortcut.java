package ru.xj2j.board.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "shortcuts")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Shortcut extends ProjectBaseModel {
    public enum ShortcutType {
        REPO("repo"),
        DIRECT("direct");

        private String value;

        ShortcutType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    @Column(nullable = false)
    private String name;

    @Column
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ShortcutType type;

    @Column
    private String url;

    @Override
    public String toString() {
        return String.format("%s <%s>", name, getProject().getName());
    }
}