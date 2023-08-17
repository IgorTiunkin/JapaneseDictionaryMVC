package com.phantom.japanese_dictionary_mvc.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class NoteDTO {


    private String romadji;


    private String kanji;


    private String hiragana;


    private String translation;
}
