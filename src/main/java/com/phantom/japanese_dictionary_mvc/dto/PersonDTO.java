package com.phantom.japanese_dictionary_mvc.dto;

import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class PersonDTO {

    @NotEmpty(message = "Имя пользователя не может быть пустым")
    @Size(min = 2, max = 100, message = "Допустимый размер от 2 и до 100 символов")
    private String username;

    @NotEmpty(message = "Пароль не может быть пустым")
    @Size (min = 2, max = 100, message = "Допустимый размер от 2 и до 100 символов")
    private String password;

    private String role;

    public PersonDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
