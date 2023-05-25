package ru.xj2j.board.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.*;


@Data
@Entity
@Table(name = "page_blocks")
public class PageBlock extends ProjectBaseModel {

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private Page page;

    private String name;

    @Column(columnDefinition = "jsonb")
    private String description;

    @Column(columnDefinition = "text")
    private String descriptionHtml = "<p></p>";

    @JsonIgnore
    @Transient
    private String descriptionStripped;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private Issue issue;

    private LocalDateTime completedAt;

    private Double sortOrder = 65535.0;

    private Boolean sync = true;

    /*
    @Override
    public void onSave() {
        if (this.getId() == null) {
            PageBlock largestSortOrder = page.getBlocks().stream()
                    .max(Comparator.comparingDouble(PageBlock::getSortOrder))
                    .orElse(null);
            if (largestSortOrder != null) {
                this.sortOrder = largestSortOrder.getSortOrder() + 10000.0;
            }
        }

        this.setDescriptionStripped(
                (this.getDescriptionHtml() == null || this.getDescriptionHtml().equals("")) ? null :
                        Jsoup.parse(this.getDescriptionHtml()).text()
        );

        if (this.getCompletedAt() != null && this.getIssue() != null) {
            State completedState = this.getProject().getStates().stream()
                    .filter(s -> s.getGroup().equals("completed"))
                    .findFirst().orElse(null);
            if (completedState != null) {
                this.getIssue().setState(completedState);
            }
        }
    } */

    @Override
    public String toString() {
        return String.format("%s <%s>", this.getPage().getName(), this.getName());
    }
}
