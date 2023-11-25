package com.phantom.japanese_dictionary_mvc.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
