package ru.xj2j.board.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "module_links")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ModuleLink extends ProjectBaseModel {
    @Column(name = "title")
    private String title;

    @Column(name = "url")
    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "module_id")
    private Module module;

    @Column(name = "metadata", columnDefinition = "json")
    private String metadata;

    @Override
    public String toString() {
        return String.format("%s %s", this.module.getName(), this.url);
    }
}
