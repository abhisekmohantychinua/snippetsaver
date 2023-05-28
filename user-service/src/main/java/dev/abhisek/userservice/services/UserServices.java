package dev.abhisek.userservice.services;

import dev.abhisek.userservice.entity.User;

import java.util.List;

public interface UserServices {

    List<User> getAllUsers();

    User getUserByUserId(String userId);

    User getUserBySnippetId(String snippetId);

    void deleteUser(User user);

    void deleteUserByUserId(String userId);

    User updateUser(String userId, User user);

    Boolean verifyUser(String userId);

}
