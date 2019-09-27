package com.nikita.simpleProject.dto.response;

import com.nikita.simpleProject.dto.user.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponseDto {
    private UserDto userDto;
    private String token;
}
