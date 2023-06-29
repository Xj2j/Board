package ru.xj2j.board.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.jsoup.Jsoup;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "page_blocks")
@Data @NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
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

    /*@JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private IssueDTO issue;*/

    private int issueId;

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

    /**@PrePersist
    public void prePersist() {
        if (this.sortOrder == null) {
            Long largestSortOrder = this.page.getPageBlocks().stream()
                    .mapToLong(PageBlock::getSortOrder)
                    .max()
                    .orElse(0L);
            this.sortOrder = largestSortOrder + 10000;
        }
        if (this.descriptionHtml != null && !this.descriptionHtml.isEmpty()) {
            this.descriptionStripped = Jsoup.parse(this.descriptionHtml).text();
        }
        if (this.completedAt != null && this.issueId != null) {
            State completedState = this.project.getStates().stream()
                    .filter(state -> state.getGroup().equals("completed"))
                    .findFirst()
                    .orElse(null);
            if (completedState != null) {
                this.issue.setState(completedState);
            }
        }
    }*/

    @Override
    public String toString() {
        return String.format("%s <%s>", this.getPage().getName(), this.getName());
    }
}
