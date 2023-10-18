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

    @NotNull (message = "Поле должно быть заполнено")
    @Min(value = 2, message = "Количество вариантов не может быть меньше двух")
    @Max(value = 10, message = "Количество вариантов не может быть больше 10")
    private Integer numberOfOptions;

    @NotNull (message = "Поле должно быть заполнено")
    @Min(value = 1, message = "Количество заданий должно быть больше нуля. Вы же хотите хотя бы одно задание:)")
    @Max(value = 20, message = "Количество заданий не может быть больше 20")
    private Integer numberOfTasks;

    public QuizRequest() {
        this.requestType = RequestType.TRANSLATION;
        numberOfOptions = 4;
        numberOfTasks = 10;
    }
}
