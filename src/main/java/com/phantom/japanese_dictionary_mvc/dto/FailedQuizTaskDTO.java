package com.phantom.japanese_dictionary_mvc.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FailedQuizTaskDTO {

    private int failedQuizTaskId;

    private String failedQuestion;
}
