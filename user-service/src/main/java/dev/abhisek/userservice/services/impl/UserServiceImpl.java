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
        return repository.findAll();
    }

    @Override
    public User getUserByUserId(String userId) {
        return repository
                .findById(userId)
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    public User getUserBySnippetId(String snippetId) {
        return getUserByUserId(snippetService
                .getSnippetBySnippetId(snippetId)
                .getUserId());
    }

    @Override
    public void addUser(User user) {
        user.setUserId(UUID
                .randomUUID()
                .toString());
        repository.save(user);
        return;
    }

    @Override
    public void deleteUser(User user) {
        repository.delete(user);
        return;
    }

    @Override
    public void deleteUserByUserId(String userId) {
        repository.deleteById(userId);
        return;
    }

    @Override
    public User updateUser(String userId) {
        User user = getUserByUserId(userId);
        User newUser = User.builder()
                .objectId(user.getObjectId())
                .userId(user.getUserId())
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .snippets(user.getSnippets())
                .build();
        return repository.save(newUser);
    }
}
