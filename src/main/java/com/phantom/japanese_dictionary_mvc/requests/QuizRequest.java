package com.phantom.japanese_dictionary_mvc.requests;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class QuizRequest {
    private RequestType requestType;

    @NotNull (message = "Значение не может быть пустым")
    @Min(value = 0, message = "Количество вариантов не может быть меньше ноля")
    @Max(value = 10, message = "Количество вариантов не может быть больше 10")
    private Integer numberOfOptions;

    @NotNull (message = "Значение не может быть пустым")
    @Min(value = 0, message = "Количество заданий не может быть меньше ноля")
    @Max(value = 20, message = "Количество заданий не может быть больше 20")
    private Integer numberOfTasks;

    public QuizRequest() {
        this.requestType = RequestType.TRANSLATION;
        numberOfOptions = 4;
        numberOfTasks = 10;
    }
}
