package com.nikita.simpleProject.controller;


import com.nikita.simpleProject.dto.response.ApiMessage;
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
            @ApiResponse(code = 200, message = "OK", response = ApiMessage.class),
            @ApiResponse(code = 400, message = "Failed to log in")
    })
    @PostMapping("/login")
    public ApiMessage login(@RequestBody User user){
        return userService.login(user);
    }

    @ApiOperation(value = "Add user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ApiMessage.class),
            @ApiResponse(code = 400, message = "Failed to create user")
    })
    @PostMapping("/addUser")
    public ApiMessage addUser(@RequestBody User user){
        return userService.addUser(user);
    }

    @ApiOperation(value = "Выводит данные о пользователе")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ApiMessage.class),
            @ApiResponse(code = 400, message = "Failed to find information")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "tokenUser", required = true, dataType = "string",
                    paramType = "header"),
    })
    @GetMapping("/main")
    public UserInfo main(Principal principal){
        return userService.getUserInfo(principal.getName());
    }

    @ApiOperation(value = "Выводит данные со второй базы")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ApiMessage.class),
            @ApiResponse(code = 400, message = "Failed to find information secondDb")
    })
    @GetMapping("/secondDb")
    public List<SomeInformation> secondDb(){
        return userService.getAllFromSecondDB();
    }
}
