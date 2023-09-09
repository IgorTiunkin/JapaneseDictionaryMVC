package com.phantom.japanese_dictionary_mvc.requests;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Setter
@Getter

public class Request {
    @NotBlank (message = "Поле должно быть заполнено")
    @Pattern(regexp = "[^,.;:(){}\\[\\]]*", message = "В запросе не должно быть знаков препинания")
    private String word;
    private RequestType requestType;
    private boolean onlyFullMatch;

    public Request() {
        this.requestType = RequestType.DEFAULT;
        this.onlyFullMatch = false;
    }
}
