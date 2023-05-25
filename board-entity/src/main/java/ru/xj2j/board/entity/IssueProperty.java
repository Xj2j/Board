package ru.xj2j.board.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

import java.util.*;

@Entity
@Table(name = "issue_properties")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class IssueProperty extends ProjectBaseModel {

    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "properties", columnDefinition = "jsonb not null default '{}'")
    @Convert(converter = JsonConverter.class)
    private Map<String, Object> properties = new HashMap<>();

    @Override
    public String toString() {
        return String.valueOf(user);
    }
}