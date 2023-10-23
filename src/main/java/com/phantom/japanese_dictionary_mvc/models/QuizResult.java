package com.phantom.japanese_dictionary_mvc.models;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

    @Column (name = "date_time")
    private LocalDateTime dateOfQuiz;

    @OneToMany (mappedBy = "quizResult", cascade = CascadeType.ALL)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<FailedQuizTask> failedQuizTasks;

    @Override
    public String toString() {
        return "QuizResult{" +
                "numberOfRightAnswers=" + numberOfRightAnswers +
                ", numberOfTasks=" + numberOfTasks +
                ", failedQuizTasks=" + failedQuizTasks +
                '}';
    }

}
