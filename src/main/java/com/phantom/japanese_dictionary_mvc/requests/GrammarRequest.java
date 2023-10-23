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
    @NotBlank(message = "Поле должно быть заполнено")
    @Pattern(regexp = "[^,.;:(){}\\[\\]]*", message = "В запросе не должно быть знаков препинания")
    private String word;

}
