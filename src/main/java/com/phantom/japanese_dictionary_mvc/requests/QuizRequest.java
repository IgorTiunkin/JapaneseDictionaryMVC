package com.phantom.japanese_dictionary_mvc.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
public class QuizRequest {

    private RequestType requestType;

    @NotNull (message = "{quizrequest.numberOfOptions.notnull}")
    @Min(value = 2, message = "{quizrequest.numberOfOptions.min}")
    @Max(value = 10, message = "{quizrequest.numberOfOptions.max}")
    private Integer numberOfOptions;

    @NotNull (message = "{quizrequest.numberOfTasks.notnull}")
    @Min(value = 1, message = "{quizrequest.numberOfTasks.min}")
    @Max(value = 20, message = "{quizrequest.numberOfTasks.max}")
    private Integer numberOfTasks;

    public QuizRequest() {
        this.requestType = RequestType.TRANSLATION;
        numberOfOptions = 4;
        numberOfTasks = 10;
    }
}
