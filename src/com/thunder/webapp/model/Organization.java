package com.thunder.webapp.model;


import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Organization {
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
}
