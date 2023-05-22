package ru.xj2j.board.board.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

@Entity
@Table(name = "pages")
@Data @NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class Page extends ProjectBaseModel {

    @Column(name = "name")
    private String name;

    @Convert(converter = JsonConverter.class)
    @Column(name = "description")
    private Map<String, Object> description = new HashMap<>();

    @Column(name = "description_html")
    private String descriptionHtml = "<p></p>";

    @Column(name = "description_stripped")
    private String descriptionStripped;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owned_by")
    private User ownedBy;

    @Column(name = "access")
    private Integer access;

    @Column(name = "color")
    private String color;

    @ManyToMany(mappedBy = "pages")
    private Set<Label> labels = new HashSet<>();

    @Override
    public String toString() {
        return ownedBy.getEmail() + " <" + name + ">";
    }

}
