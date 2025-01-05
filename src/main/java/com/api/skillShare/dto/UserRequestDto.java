package com.api.skillShare.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Optional;

@Getter
@NoArgsConstructor
public class UserRequestDto {

    @NotBlank(message = "Name is required")
    @Setter
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    @Setter
    private String email;

    private Optional<String> bio;

    public UserRequestDto(String name, String email, String bio) {
        this.name = name;
        this.email = email;
        this.bio = Optional.ofNullable(bio);
    }

    public void setBio(String bio) {
        this.bio = Optional.ofNullable(bio);
    }
}
