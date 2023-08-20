package com.phantom.japanese_dictionary_mvc.requests;

import lombok.Getter;
import lombok.Setter;
import lombok.Value;

import javax.validation.constraints.*;

@Setter
@Getter
public class WritePracticeRequest {
    private RequestType requestType;

    @NotNull(message = "Поле должно быть заполнено")
    //@Digits (integer = 3, fraction = 0, message = "Нужно ввести число")
    @Min(value = 1, message = "Количество заданий должно быть больше нуля. Вы же хотите хотя бы одно задание:)")
    @Max(value = 20, message = "Количество заданий должно быть не больше 20")
    private Integer quantity;

    public WritePracticeRequest() {
        this.requestType = RequestType.DEFAULT;
        this.quantity = 10;
    }
}
