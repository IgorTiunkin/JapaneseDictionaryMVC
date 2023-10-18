package com.phantom.japanese_dictionary_mvc.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuizTask {
    private Integer number;
    private String question;
    private String rightAnswer;
    private List<String> options = new ArrayList<>();


    @Override
    public String toString() {
        return "QuizTask{" +
                "number=" + number +
                ", question='" + question + '\'' +
                ", rightAnswer='" + rightAnswer + '\'' +
                ", options=" + options +
                '}';
    }
}
