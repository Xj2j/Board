package ru.xj2j.board.userteamservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
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