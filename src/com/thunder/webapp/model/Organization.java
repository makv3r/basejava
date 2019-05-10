package com.thunder.webapp.model;


import com.thunder.webapp.util.LocalDateAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class Organization implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final Organization EMPTY = new Organization(new Link("",""), Activity.EMPTY);

    private Link link;
    private List<Activity> activities;

    public Organization() {
    }

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
        StringBuilder sb = new StringBuilder();
        sb.append(link.getName());
        String url = link.getUrl();
        if (url != null) sb.append("\turl: ").append(url);
        for (Activity activity : activities) {
            sb.append(activity);
        }
        sb.append(System.lineSeparator());
        return sb.toString();
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Activity implements Serializable {
        private static final long serialVersionUID = 1L;

        public static final Activity EMPTY = new Activity();

        @XmlJavaTypeAdapter(LocalDateAdapter.class)
        private LocalDate startDate;
        @XmlJavaTypeAdapter(LocalDateAdapter.class)
        private LocalDate endDate;
        private String title;
        private String description;

        public Activity() {
        }

        public Activity(LocalDate startDate, LocalDate endDate, String title, String description) {
            Objects.requireNonNull(startDate, "startDate must not be null");
            Objects.requireNonNull(endDate, "endDate must not be null");
            Objects.requireNonNull(title, "title must not be null");
            this.startDate = startDate;
            this.endDate = endDate;
            this.title = title;
            this.description = description == null ? "" : description;
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
            StringBuilder sb = new StringBuilder();
            int startMonth = startDate.getMonthValue();
            int endMonth = endDate.getMonthValue();
            sb.append(System.lineSeparator()).
                    append(startMonth < 10 ? "0" + startMonth : startMonth).
                    append("/").
                    append(startDate.getYear()).
                    append("-").
                    append(endMonth < 10 ? "0" + endMonth : endMonth).
                    append("/").
                    append(endDate.getYear()).
                    append(" ").
                    append(title).
                    append(System.lineSeparator());
            if (description != null) sb.append(description);
            return sb.toString();
        }
    }
}
