package com.api.skillShare.controller;

import com.api.skillShare.constraint.ValidUUID;
import com.api.skillShare.dto.UserRequestDto;
import com.api.skillShare.model.User;
import com.api.skillShare.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping(value = "/api/users", produces = APPLICATION_JSON_VALUE)
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody @Valid @NotNull UserRequestDto userRequestDto) {
        User user = userService.createUser(userRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable @NotBlank @ValidUUID final String userId) {
        User user = userService.getUserById(userId);
        return ResponseEntity.ok(user);
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable @NotBlank final String userId,
                                           @RequestBody @Valid @NotNull UserRequestDto userRequestDto) {
        User user = userService.updateUser(userId, userRequestDto);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable @NotBlank @ValidUUID final String userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok("User: " + userId + " deleted successfully");
    }
}
