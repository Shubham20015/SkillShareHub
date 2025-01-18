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

    private Optional<@Size(max = 300, message = "Bio must be lesser than 300 characters long") String> bio = Optional.empty();

    public UserRequestDto(String bio) {
        this.bio = Optional.ofNullable(bio);
    }

    public void setBio(String bio) {
        this.bio = Optional.ofNullable(bio);
    }
}
