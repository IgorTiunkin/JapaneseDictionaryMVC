package com.phantom.japanese_dictionary_mvc.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FailedQuizTaskDTO {

    private int failedQuizTaskId;

    private String failedQuestion;
}
