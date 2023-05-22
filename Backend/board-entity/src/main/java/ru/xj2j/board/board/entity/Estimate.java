package ru.xj2j.board.board.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Table(name = "estimates")
@Data
public class Estimate extends ProjectBaseModel {

    @Column(name = "name", length = 255)
    @Size(max = 255)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Override
    public String toString() {
        return String.format("%s <%s>", name, getProject().getName());  //project.getName()
    }
}