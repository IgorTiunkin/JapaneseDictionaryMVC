package com.phantom.japanese_dictionary_mvc.requests;

import java.util.Map;

public enum RequestType {
    TRANSLATION (Map.of("ru", "Перевод", "en", "Translation", "ja", "翻訳")),
    SPELLING (Map.of("ru", "Транслитерация", "en", "Spelling", "ja", "ローマ字")),
    KANJI(Map.of("ru", "Иероглифы", "en", "Kanji", "ja", "漢字")),
    KANA (Map.of("ru", "Кана", "en","Kana", "ja", "かな")),
    DEFAULT (Map.of("ru", "По умолчанию", "en", "Default", "ja", "デフォルト"));

    private final Map<String, String> text;
    RequestType(Map<String, String> text) {
        this.text = text;
    }

    public Map<String, String> getText() {
        return text;
    }
}
