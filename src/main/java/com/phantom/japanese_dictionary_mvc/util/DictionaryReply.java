package com.phantom.japanese_dictionary_mvc.util;

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

    private List <NoteDTO> fullMatchNoteDTOS;

    private List <NoteDTO> partialMatchNoteDTOS;

    private int fullMatchCount;

    private int partialMatchCount;

    private boolean isFullMatchVisible;

    private boolean isPartialMatchVisible;
}
