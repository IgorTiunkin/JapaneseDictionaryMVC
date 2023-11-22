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

    @NotEmpty(message = "{persondto.username.not-empty}")
    @Size(min = 2, max = 100, message = "{persondto.username.size}")
    private String username;

    @NotEmpty(message = "{persondto.password.not-empty}")
    @Size (min = 2, max = 100, message = "{persondto.password.size}")
    private String password;

    private String role;

    public PersonDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
