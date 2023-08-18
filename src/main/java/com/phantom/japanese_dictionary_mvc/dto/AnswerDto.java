package com.phantom.japanese_dictionary_mvc.dto;


import com.phantom.japanese_dictionary_mvc.models.Answer;

import java.util.ArrayList;
import java.util.List;

public class AnswerDto {
    private List<Answer> answers = new ArrayList<>();

    public AnswerDto() {
    }

    public AnswerDto(List<Answer> answers) {
        this.answers = answers;
    }

    public void addAnswer(Answer answer) {
        this.answers.add(answer);
    }


    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }
}
