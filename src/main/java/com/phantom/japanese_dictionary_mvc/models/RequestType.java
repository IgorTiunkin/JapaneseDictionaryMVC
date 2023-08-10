package com.phantom.japanese_dictionary_mvc.models;

public enum RequestType {
    TRANSLATION ("Перевод"), SPELLING ("Транслитерация"), KANJI("Иероглифы"), KANA ("Кана"),
     DEFAULT ("По умолчанию") ;

    private final String text;
    RequestType(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
