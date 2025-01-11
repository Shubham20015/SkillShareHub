package com.api.skillShare.service.Impl;

import com.api.skillShare.dto.UserRequestDto;
import com.api.skillShare.exception.ResourceNotFoundException;
import com.api.skillShare.model.User;
import com.api.skillShare.repository.UserRepository;
import com.api.skillShare.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequestScope
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User createUser(UserRequestDto userRequestDto) {
        User user = User.builder()
                .name(userRequestDto.getName())
                .email(userRequestDto.getEmail())
                .bio(userRequestDto.getBio().orElse(null))
                .build();
        return userRepository.save(user);
    }

    @Override
    public User updateUser(String userId, UserRequestDto userRequestDto) {
        UUID id = UUID.fromString(userId);
        Optional<User> user = userRepository.findById(id);

        if (user.isPresent()) {
            User updatedUser = user.get();
            if(userRequestDto.getName() != null) updatedUser.setName(userRequestDto.getName());
            if(userRequestDto.getEmail() != null) updatedUser.setEmail(userRequestDto.getEmail());
            if(userRequestDto.getBio().isPresent()) updatedUser.setBio(userRequestDto.getBio().get());

            return userRepository.save(updatedUser);
        } else {
            throw new ResourceNotFoundException("User: " + userId + " doesn't exists");
        }
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(String userId) {
        Optional<User> user = userRepository.findById(UUID.fromString(userId));
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new ResourceNotFoundException("User: " + userId + " doesn't exists");
        }
    }

    @Override
    public void deleteUser(String userId) {
        UUID id = UUID.fromString(userId);
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User: " + userId + " doesn't exists");
        }

        userRepository.deleteById(id);
    }
}
