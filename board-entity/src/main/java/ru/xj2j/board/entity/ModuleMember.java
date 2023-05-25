package ru.xj2j.board.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "module_members")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ModuleMember extends ProjectBaseModel {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "module_id")
    private Module module;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User member;

    @Override
    public String toString() {
        return String.format("%s %s", this.module.getName(), this.member.getEmail());
    }
}
