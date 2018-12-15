package com.thunder.webapp.model;


import java.util.Objects;

public class SectionText extends SectionData {
    private final String text;

    public SectionText(String text) {
        Objects.requireNonNull(text, "text must not be null");
        this.text = text;
    }

    public String getText() {
        return text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SectionText that = (SectionText) o;
        return text.equals(that.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text);
    }

    @Override
    public String toString() {
        return "SectionText{" +
                "text='" + text + '\'' +
                '}';
    }
}
