package com.phantom.japanese_dictionary_mvc.util;

import com.phantom.japanese_dictionary_mvc.finders.dictionary.KanaWordFinder;
import com.phantom.japanese_dictionary_mvc.finders.dictionary.KanjiWordFinder;
import com.phantom.japanese_dictionary_mvc.finders.dictionary.RomajiWordFinder;
import org.springframework.stereotype.Component;

@Component
public class LanguageDefiner {

    public LanguageType defineLanguage(String wordToDefine) {
        if (wordToDefine.matches("[A-Za-z]+")) return LanguageType.ENGLISH;
        if (wordToDefine.matches(".*\\p{script=Han}+.*")) return LanguageType.KANJI;
        if (wordToDefine.matches(".*\\p{script=Hiragana}+.*") ||
                wordToDefine.matches(".*\\p{script=Katakana}+.*")) {
            return LanguageType.KANA;
        }
        if (wordToDefine.matches("[А-Яа-я]+")) return LanguageType.RUSSIAN;
        return LanguageType.UNKNOWN;
    }
}
