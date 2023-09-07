package com.phantom.japanese_dictionary_mvc.replies;

import com.phantom.japanese_dictionary_mvc.dto.GrammarNoteDTO;
import com.phantom.japanese_dictionary_mvc.dto.NoteDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class GrammarDictionaryReply {
    private List<GrammarNoteDTO> grammarNoteDTOS;

    private int matchCount;

    private int indexOfLastPage;
}
