package com.phantom.japanese_dictionary_mvc.util;

import com.phantom.japanese_dictionary_mvc.dto.NoteDTO;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BaseGenericConverter {

    private final ModelMapper modelMapper;

    public BaseGenericConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public <T> List<T> getNotesToShowForCurrentPage(List<T> notesFromRepository, Integer page,
                                                 int LIMIT_OF_NOTES_IN_VIEW, int NOTES_PER_PAGE) {
        int indexOfLastNote = Math.min(notesFromRepository.size(), LIMIT_OF_NOTES_IN_VIEW);
        Integer currentPage = Math.min(page, notesFromRepository.size()/NOTES_PER_PAGE);
        List <T> notesToShowForCurrentPage = new ArrayList<>();
        for (int i = currentPage*NOTES_PER_PAGE;
             i < Math.min ((currentPage+1)*NOTES_PER_PAGE, indexOfLastNote); i++) {
            notesToShowForCurrentPage.add(notesFromRepository.get(i));
        }
        return notesToShowForCurrentPage;
    }


    public <T, V> List <V> convertNoteToNoteDTO(List <T> notes, Class <V> vClass) {
        List <V> noteDTOS = new ArrayList<>();
        for (T note: notes) noteDTOS.add(modelMapper.map(note, vClass));
        return noteDTOS;
    }


}
