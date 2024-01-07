package ru.boganov.coursework.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;

    @NotEmpty
    private String name;

    @NotEmpty(message = "Имя не должно быть пустым")
    private String username;

    @NotEmpty(message = "Пароль не должен быть пустым")
    private String password;
}