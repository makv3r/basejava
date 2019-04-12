package com.thunder.webapp.model;


import java.io.Serializable;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Organization implements Serializable {
    private static final long serialVersionUID = 1L;

    private final Link link;
    private final List<Activity> activities;

    public Organization(Link link, Activity... activities) {
        this(link, Arrays.asList(activities));
    }

    public Organization(Link link, List<Activity> activities) {
        Objects.requireNonNull(link, "link must not be null");
        Objects.requireNonNull(activities, "activities must not be null");
        this.link = link;
        this.activities = activities;
    }

    public Link getLink() {
        return link;
    }

    public List<Activity> getActivities() {
        return activities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization that = (Organization) o;
        return link.equals(that.link) &&
                activities.equals(that.activities);
    }

    @Override
    public int hashCode() {
        return Objects.hash(link, activities);
    }

    @Override
    public String toString() {
        return "Organization{" +
                "link=" + link +
                ", activities=" + activities +
                '}';
    }

    public static class Activity implements Serializable {
        private static final long serialVersionUID = 1L;

        private final LocalDate startDate;
        private final LocalDate endDate;
        private final String title;
        private final String description;

        public Activity(LocalDate startDate, LocalDate endDate, String title, String description) {
            Objects.requireNonNull(startDate, "startDate must not be null");
            Objects.requireNonNull(endDate, "endDate must not be null");
            Objects.requireNonNull(title, "title must not be null");
            this.startDate = startDate;
            this.endDate = endDate;
            this.title = title;
            this.description = description;
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
            Activity activity = (Activity) o;
            return startDate.equals(activity.startDate) &&
                    endDate.equals(activity.endDate) &&
                    title.equals(activity.title) &&
                    Objects.equals(description, activity.description);
        }

        @Override
        public int hashCode() {
            return Objects.hash(startDate, endDate, title, description);
        }

        @Override
        public String toString() {
            return "Activity{" +
                    "startDate=" + startDate +
                    ", endDate=" + endDate +
                    ", title='" + title + '\'' +
                    ", description='" + description + '\'' +
                    '}';
        }
    }
}
