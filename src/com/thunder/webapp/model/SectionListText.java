package com.thunder.webapp.model;


import java.util.List;
import java.util.Objects;

public class SectionListText extends SectionData {
    private final List<String> list;

    public SectionListText(List<String> list) {
        this.list = list;
    }

    public List<String> getList() {
        return list;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SectionListText that = (SectionListText) o;
        return list.equals(that.list);
    }

    @Override
    public int hashCode() {
        return Objects.hash(list);
    }

    @Override
    public String toString() {
        return "SectionListText{" +
                "list=" + list +
                '}';
    }
}
