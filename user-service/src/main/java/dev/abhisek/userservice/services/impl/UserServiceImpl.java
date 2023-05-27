package dev.abhisek.userservice.services.impl;

import dev.abhisek.userservice.entity.User;
import dev.abhisek.userservice.exception.UserNotFoundException;
import dev.abhisek.userservice.repository.UserRepository;
import dev.abhisek.userservice.services.UserServices;
import dev.abhisek.userservice.services.external.SnippetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserServices {
    private final UserRepository repository;
    private final SnippetService snippetService;


    @Override
    public List<User> getAllUsers() {
        List<User> users = repository.findAll();
        users = users
                .stream()
                .peek(user -> user
                        .setSnippets(snippetService
                                .getSnippetByUserId(user
                                        .getUserId()))).toList();
        return users;
    }

    @Override
    public User getUserByUserId(String userId) {
        User user = repository
                .findById(userId)
                .orElseThrow(UserNotFoundException::new);
        user.setSnippets(snippetService
                .getSnippetByUserId(user.getUserId()));

        return user;
    }

    @Override
    public User getUserBySnippetId(String snippetId) {
        return repository
                .findById(snippetService
                        .getSnippetBySnippetId(snippetId)
                        .getUserId())
                .orElseThrow(new UserNotFoundException("Provided snippet Id does not belongs to any user."));
    }

    @Override
    public void addUser(User user) {
        user.setUserId(UUID
                .randomUUID()
                .toString());
        user.setRoles(List.of("ROLE_USER"));
        repository.save(user);

    }

    @Override
    public void deleteUser(User user) {
        repository.delete(user);
    }

    @Override
    public void deleteUserByUserId(String userId) {
        repository.deleteById(userId);
    }

    @Override
    public User updateUser(String userId, User user) {
        User newUser = repository
                .findById(userId)
                .orElseThrow(UserNotFoundException::new);
        newUser.setName(user.getName());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(user.getPassword());

        return repository.save(newUser);
    }
}
