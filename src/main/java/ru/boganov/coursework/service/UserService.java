package ru.boganov.coursework.service;

import ru.boganov.coursework.dto.UserDto;
import ru.boganov.coursework.entity.User;

import java.util.List;

public interface UserService {
    void saveUser(UserDto userDto);

    User findUserByUsername(String username);

    List<UserDto> findAllUsers();

    void addRoleToUser(String username, String roleName);

}