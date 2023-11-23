package com.phantom.japanese_dictionary_mvc.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;

import javax.validation.constraints.*;

@Setter
@Getter
@AllArgsConstructor
public class WritePracticeRequest {

    private RequestType requestType;

    @NotNull(message = "{writepracticerequest.quantity.notnull}")
    @Min(value = 1, message = "{writepracticerequest.quantity.min}")
    @Max(value = 20, message = "{writepracticerequest.quantity.max}")
    private Integer quantity;

    public WritePracticeRequest() {
        this.requestType = RequestType.TRANSLATION;
        this.quantity = 10;
    }
}
