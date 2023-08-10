package com.phantom.japanese_dictionary_mvc.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


public class Request {
    private String word;

    public Request() {
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }
}
