package com.phantom.japanese_dictionary_mvc.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Setter
@Getter
@AllArgsConstructor
public class Request {

    @NotBlank (message = "{request.word.notblank}")
    @Pattern(regexp = "[^,.;:(){}\\[\\]]*", message = "{request.word.pattern}")
    private String word;
    private RequestType requestType;
    private boolean onlyFullMatch;

    public Request() {
        this.requestType = RequestType.DEFAULT;
        this.onlyFullMatch = false;
    }
}
