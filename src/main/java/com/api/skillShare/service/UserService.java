package com.api.skillShare.service;

import com.api.skillShare.dto.UserRequestDto;
import com.api.skillShare.model.User;

import java.util.List;

public interface UserService {
    User updateUser(String userId, UserRequestDto userRequestDto);
    List<User> getUsers();
    User getUserById(String userId);
    void deleteUser(String userId);
}
