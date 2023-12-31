package com.phantom.japanese_dictionary_mvc.models;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

    public FailedQuizTask(QuizResult quizResult, String failedQuestion) {
        this.quizResult = quizResult;
        this.failedQuestion = failedQuestion;
    }

    @Override
    public String toString() {
        return "FailedQuizTask{" +
                "failedQuestion='" + failedQuestion + '\'' +
                '}';
    }
}
