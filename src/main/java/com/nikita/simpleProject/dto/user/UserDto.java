package com.nikita.simpleProject.dto.user;

import com.nikita.simpleProject.model.first.Role;
import com.nikita.simpleProject.model.first.User;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@Builder
public class UserDto {
    @NotNull
    private String username;
    private Boolean active;
    private Set<Role> roles;

    public static UserDto toDto(User user){
        return UserDto.builder()
                .active(user.isUserEnabled())
                .roles(user.getRoles())
                .username(user.getLogin())
                .build();
    }
}
