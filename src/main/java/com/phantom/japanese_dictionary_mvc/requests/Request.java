package com.phantom.japanese_dictionary_mvc.requests;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Setter
@Getter

public class Request {
    @NotEmpty (message = "Поле должно быть заполнено")
    private String word;
    private RequestType requestType;
    private boolean onlyFullMatch;

    public Request() {
        this.requestType = RequestType.DEFAULT;
        this.onlyFullMatch = false;
    }
}