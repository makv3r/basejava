package com.thunder.webapp.model;


import java.time.LocalDate;
import java.util.Objects;

public class Content {
    private final Link link;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final String title;
    private final String description;

    public Content(Link link, LocalDate startDate, LocalDate endDate, String title, String description) {
        Objects.requireNonNull(link, "link must not be null");
        Objects.requireNonNull(startDate, "startDate must not be null");
        Objects.requireNonNull(endDate, "endDate must not be null");
        Objects.requireNonNull(title, "title must not be null");
        this.link = link;
        this.startDate = startDate;
        this.endDate = endDate;
        this.title = title;
        this.description = description;
    }

    public Link getLink() {
        return link;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Content content = (Content) o;
        return link.equals(content.link) &&
                startDate.equals(content.startDate) &&
                endDate.equals(content.endDate) &&
                title.equals(content.title) &&
                description.equals(content.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(link, startDate, endDate, title, description);
    }

    @Override
    public String toString() {
        return "Content{" +
                "link=" + link +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", title=" + title +
                ", description=" + description +
                '}';
    }
}
