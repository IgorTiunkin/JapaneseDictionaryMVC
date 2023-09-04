package com.phantom.japanese_dictionary_mvc.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table (name = "results")
public class QuizResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int quizResultId;

    @ManyToOne
    @JoinColumn (name = "user_id", referencedColumnName = "id")
    private Person user;

    @Column(name = "number_of_right_answers")
    private int numberOfRightAnswers;

    @Column(name = "number_of_tasks")
    private int numberOfTasks;

    /*@OneToMany (mappedBy = "quizResult")
    private List<FailedQuizTask> failedTasksQuestion;*/


}
