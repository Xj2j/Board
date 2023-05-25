package ru.xj2j.board.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

@Entity
@Table(name = "estimate_points")
@Data
public class EstimatePoint extends ProjectBaseModel {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estimate_id", nullable = false)
    private Estimate estimate;

    @Column(name = "key")
    @Min(0) @Max(7)
    private Integer key;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "value", length = 20)
    @Size(max = 20)
    private String value;

    @Override
    public String toString() {
        return String.format("%s <%s> <%s>", estimate.getName(), key, value);
    }
}
