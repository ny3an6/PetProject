package com.nikita.simpleProject.controller;


import com.nikita.simpleProject.dto.response.LoginResponseDto;
import com.nikita.simpleProject.dto.user.UserDto;
import com.nikita.simpleProject.exception.RegistrationException;
import com.nikita.simpleProject.model.first.User;
import com.nikita.simpleProject.model.first.UserInfo;
import com.nikita.simpleProject.model.second.SomeInformation;
import com.nikita.simpleProject.service.UserService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
public class FirstController {

   @Autowired
   private UserService userService;

    @ApiOperation(value = "Log in user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Failed to log in")
    })
    @PostMapping("/login")
    public LoginResponseDto login(@RequestBody User user) throws RegistrationException {
        return userService.login(user);
    }

    @ApiOperation(value = "Registry")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Failed to registry")
    })
    @PostMapping("/registry")
    public UserDto addUser(@RequestBody User user) throws RegistrationException {
        return userService.addUser(user);
    }

    @ApiOperation(value = "Showing data about user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Failed to find user information")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "tokenUser", required = true, dataType = "string",
                    paramType = "header"),
    })
    @GetMapping("/main")
    public UserInfo main(Principal principal){
        return userService.getUserInfo(principal.getName());
    }

    @ApiOperation(value = "Showing data from second db")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Failed to find information secondDb")
    })
    @GetMapping("/secondDb")
    public List<SomeInformation> secondDb(){
        return userService.getAllFromSecondDB();
    }
}
