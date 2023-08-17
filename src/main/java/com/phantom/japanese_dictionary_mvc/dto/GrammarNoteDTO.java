package com.phantom.japanese_dictionary_mvc.dto;

import lombok.*;

import javax.persistence.Column;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GrammarNoteDTO {


    private String source;


    private String rule;


    private String explanation;


    private String example;
}
