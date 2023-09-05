package com.phantom.japanese_dictionary_mvc.mappers;

import com.phantom.japanese_dictionary_mvc.dto.FailedQuizTaskDTO;
import com.phantom.japanese_dictionary_mvc.models.FailedQuizTask;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class FailedQuizTaskToDTOMapper {

    private final ModelMapper modelMapper;

    public FailedQuizTaskToDTOMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public FailedQuizTaskDTO failedQuizTaskToDTO (FailedQuizTask failedQuizTask) {
        return modelMapper.map(failedQuizTask, FailedQuizTaskDTO.class);
    }

}
