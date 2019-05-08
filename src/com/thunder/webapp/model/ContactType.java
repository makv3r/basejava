package com.thunder.webapp.model;

public enum ContactType {
    TEL("Тел"),
    SKYPE("Skype"),
    MAIL("Почта"),
    GITHUB("Профиль Github"),
    LINKEDIN("Профиль LinkedIn"),
    STACKOVERFLOW("Профиль StackOverflow"),
    LINK("Домашняя страница");

    private String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String toHtml(String value) {
        if (value != null) {
            String typeLink = "";
            switch (this) {
                case TEL:
                    typeLink = "tel:";
                    break;
                case SKYPE:
                    typeLink = "skype:";
                    break;
                case MAIL:
                    typeLink = "mailto:";
                    break;
                case GITHUB:
                case LINKEDIN:
                case STACKOVERFLOW:
                default:
            }
            return "<a href='" + typeLink + value + "'>" + value + "</a>";
        }
        return "";
    }

    public String toLink(String href) {
        return toLink(href, title);
    }

    public static String toLink(String href, String title) {
        return "<a href='" + href + "'>" + title + "</a>";
    }
}