package com.phantom.japanese_dictionary_mvc.replies;

import com.phantom.japanese_dictionary_mvc.dto.NoteDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class DictionaryReply {

    private List <NoteDTO> NoteDTOS;

    private int fullMatchCount;

    private int partialMatchCount;

    private int indexOfLastPage;

}
