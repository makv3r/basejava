package com.thunder.webapp.model;


import java.util.List;
import java.util.Objects;

public class SectionListContent extends SectionData {
    private final List<Content> contentList;

    public SectionListContent(List<Content> contentList) {
        this.contentList = contentList;
    }

    public List<Content> getContentList() {
        return contentList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SectionListContent that = (SectionListContent) o;
        return contentList.equals(that.contentList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(contentList);
    }

    @Override
    public String toString() {
        return "SectionListContent{" +
                "contentList=" + contentList +
                '}';
    }
}
