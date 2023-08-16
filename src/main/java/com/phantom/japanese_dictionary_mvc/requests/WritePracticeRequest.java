package com.phantom.japanese_dictionary_mvc.requests;

import lombok.Getter;
import lombok.Setter;
import lombok.Value;

import javax.validation.constraints.*;

@Setter
@Getter
public class WritePracticeRequest {
    private RequestType requestType;

    @NotNull(message = "Значение не должно быть пустым.")
    @Min(value = 0, message = "Значение должно быть больше ноля.")
    @Max(value = 20, message = "Значение должно быть менее 21")
    private Integer quantity;

    public WritePracticeRequest() {
        this.requestType = RequestType.DEFAULT;
        this.quantity = 10;
    }
}
