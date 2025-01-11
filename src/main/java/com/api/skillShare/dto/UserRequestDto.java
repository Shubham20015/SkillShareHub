package com.api.skillShare.dto;

import com.api.skillShare.service.marker.PostValidationGroup;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Optional;

@Getter
@NoArgsConstructor
public class UserRequestDto {

    @NotBlank(groups = PostValidationGroup.class, message = "Name is required")
    @Size(min = 3, max = 40, message = "Name must be between 3 and 40 characters long")
    @Setter
    private String name;

    @NotBlank(groups = PostValidationGroup.class, message = "Email is required")
    @Email(message = "Email should be valid")
    @Setter
    private String email;

    private Optional<@Size(max = 300, message = "Bio must be lesser than 300 characters long") String> bio = Optional.empty();

    public UserRequestDto(String name, String email, String bio) {
        this.name = name;
        this.email = email;
        this.bio = Optional.ofNullable(bio);
    }

    public void setBio(String bio) {
        this.bio = Optional.ofNullable(bio);
    }
}
