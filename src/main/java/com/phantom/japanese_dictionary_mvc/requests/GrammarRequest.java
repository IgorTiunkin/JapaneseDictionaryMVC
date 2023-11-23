package com.phantom.japanese_dictionary_mvc.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GrammarRequest {
    @NotBlank(message = "{grammarrequest.word.notblank}")
    @Pattern(regexp = "[^,.;:(){}\\[\\]]*", message = "{grammarrequest.word.pattern}")
    private String word;
}
