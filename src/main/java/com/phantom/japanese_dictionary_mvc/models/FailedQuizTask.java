package com.phantom.japanese_dictionary_mvc.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "failed_quiz_tasks")
public class FailedQuizTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int failedQuizTaskId;

    @ManyToOne
    @JoinColumn(name = "quiz_result_id", referencedColumnName = "id")
    private QuizResult quizResult;

    @Column(name = "failed_question")
    private String failedQuestion;
}
