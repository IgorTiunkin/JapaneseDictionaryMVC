package com.phantom.japanese_dictionary_mvc.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter

public class Request {
    private String word;
    private RequestType requestType;
    private boolean onlyFullMatch;

    public Request() {
        this.requestType = RequestType.DEFAULT;
        this.onlyFullMatch = false;
    }
}
