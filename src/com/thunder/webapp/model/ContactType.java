package com.thunder.webapp.model;

public enum ContactType {
    TEL("Тел"),
    SKYPE("Skype"),
    MAIL("Почта"),
    GITHUB("Профиль Github"),
    LINKEDIN("Профиль LinkedIn"),
    STACKOVERFLOW("Профиль StackOverflow"),
    LINK("Ссылка");

    private String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}