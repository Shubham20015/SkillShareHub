package com.api.skillShare.service.Impl;

import com.api.skillShare.dto.UserRequestDto;
import com.api.skillShare.exception.ResourceNotFoundException;
import com.api.skillShare.model.User;
import com.api.skillShare.repository.UserRepository;
import com.api.skillShare.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import javax.crypto.SecretKey;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequestScope
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    @Value("${jwt.secret}")
    private String secret;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HttpServletRequest request;

    private SecretKey secretKey;

    @PostConstruct
    public void init() {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes());
    }

    @Override
    public User updateUser(String userId, UserRequestDto userRequestDto) {
        UUID id = UUID.fromString(userId);
        Optional<User> user = userRepository.findById(id);

        if (user.isPresent()) {
            User updatedUser = user.get();
            if(userRequestDto.getBio().isPresent()) updatedUser.setBio(userRequestDto.getBio().get());

            return userRepository.save(updatedUser);
        } else {
            return createUser(userRequestDto);
        }
    }

    @Override
    public List<User> getUsers() {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            throw new ResourceNotFoundException("No registered users right now");
        } else {
            return users;
        }
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

    private User createUser(UserRequestDto userRequestDto) {
        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        Claims claims = getAllClaims(bearerToken.substring(7));

        String email = claims.get("email", String.class);

        if (userRepository.existsByEmail(email)) {
            throw new ResourceNotFoundException("Given userId is not correct");
        }

        User user = User.builder()
                .name(claims.get("username", String.class))
                .email(email)
                .bio(userRequestDto.getBio().orElse(null))
                .build();
        return userRepository.save(user);
    }

    private Claims getAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
