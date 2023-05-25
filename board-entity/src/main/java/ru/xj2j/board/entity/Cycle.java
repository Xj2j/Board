package ru.xj2j.board.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

@Entity(name = "Cycle")
@Table(name = "cycles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cycle extends ProjectBaseModel {

    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "start_date")
    private Date startDate;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "end_date")
    private Date endDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User ownedBy;

    @Override
    public String toString() {
        return String.format("%s <%s>", this.getName(), this.getProject().getName());
    }
}