package com.phantom.japanese_dictionary_mvc.models;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class QuizTask {
    private Integer number;
    private String question;
    private String rightAnswer;
    private List<String> options = new ArrayList<>();
    private String userAnswer;

    public QuizTask() {
    }

    @Override
    public String toString() {
        return "QuizTask{" +
                "number=" + number +
                ", question='" + question + '\'' +
                ", rightAnswer='" + rightAnswer + '\'' +
                ", options=" + options +
                ", userAnswer='" + userAnswer + '\'' +
                '}';
    }
}
