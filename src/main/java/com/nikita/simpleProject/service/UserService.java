package com.nikita.simpleProject.service;

import com.nikita.simpleProject.annotation.NotNullResult;
import com.nikita.simpleProject.dto.ApiResult;
import com.nikita.simpleProject.dto.response.ApiMessage;
import com.nikita.simpleProject.exception.DefaultException;
import com.nikita.simpleProject.exception.UserExistsException;
import com.nikita.simpleProject.model.first.Role;
import com.nikita.simpleProject.model.first.State;
import com.nikita.simpleProject.model.first.User;
import com.nikita.simpleProject.model.first.UserInfo;
import com.nikita.simpleProject.repository.firstrepository.UserInfoRepository;
import com.nikita.simpleProject.security.JwtTokenProvider;
import com.nikita.simpleProject.repository.firstrepository.UserRepository;
import com.nikita.simpleProject.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserInfoRepository userInfoRepository;

    public ApiMessage login(User user) {
        String username = user.getLogin();
        String password = user.getPassword();
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            User dbUser = userRepository.findUserByLogin(username);
            String token = jwtTokenProvider.createToken(username, dbUser.getRoles());
            UserUtils.removePassword(dbUser);
            return new ApiMessage(HttpStatus.OK, token, dbUser, ApiResult.SUCCESS);

        } catch (AuthenticationException e) {
            throw new DefaultException(HttpStatus.UNPROCESSABLE_ENTITY, "Invalid username or password supplied");
        }
    }

    public ApiMessage addUser(User user) throws UserExistsException, DefaultException {
        String username = user.getLogin().toLowerCase();
        User dbUser = userRepository.findUserByLogin(username);
        if (dbUser != null) {
            throw new UserExistsException();
        }
        user.setLogin(username);
        user.setRoles(Collections.singleton(Role.ROLE_USER));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setState(State.ACTIVE);
        userRepository.save(user);
        UserUtils.removePassword(user);
        String token = jwtTokenProvider.createToken(user.getLogin(), user.getRoles());
        return new ApiMessage(HttpStatus.OK, token, user, ApiResult.SUCCESS);
    }

    public UserInfo getUserInfo(String userName){
        Optional<UserInfo> userInfoCandidate = userInfoRepository.findByUserName(userName);
        if (userInfoCandidate.isPresent()) {
            userInfoCandidate.get().getUserName().setPassword("");
            return userInfoCandidate.get();
        }else throw new IllegalArgumentException("No information about this user");
    }

    @NotNullResult
    public User findUserByUsername(@NotNull String username) throws DefaultException {
        return userRepository.findUserByLogin(username);
    }
}