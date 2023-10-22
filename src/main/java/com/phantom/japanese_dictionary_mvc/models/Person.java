package com.phantom.japanese_dictionary_mvc.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "Person")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Person {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int personId;

    @NotEmpty(message = "Имя пользователя не может быть пустым")
    @Size (min = 2, max = 100, message = "Допустимый размер от 2 и до 100 символов")
    @Column(name = "username")
    private String username;

    @NotEmpty(message = "Пароль не может быть пустым")
    @Size (min = 2, max = 100, message = "Допустимый размер от 2 и до 100 символов")
    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private String role;

    @OneToMany (mappedBy = "user")
    private List<QuizResult> quizResultList;

    public Person(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public String toString() {
        return "Person{" +
                "personId=" + personId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
