package com.thunder.webapp.model;

public enum ContactType {
    TEL("Тел"),
    SKYPE("Skype"),
    MAIL("Почта"),
    PROFILE("Профиль"),
    LINK("Ссылка");

    private String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "ContactType{" +
                "title='" + title + '\'' +
                '}';
    }
}
