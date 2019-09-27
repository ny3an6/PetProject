package com.nikita.simpleProject.service;


import com.nikita.simpleProject.dto.response.LoginResponseDto;
import com.nikita.simpleProject.dto.user.UserDto;
import com.nikita.simpleProject.exception.DefaultException;
import com.nikita.simpleProject.exception.RegistrationException;
import com.nikita.simpleProject.model.first.Role;
import com.nikita.simpleProject.model.first.State;
import com.nikita.simpleProject.model.first.User;
import com.nikita.simpleProject.model.first.UserInfo;
import com.nikita.simpleProject.model.second.SomeInformation;
import com.nikita.simpleProject.repository.firstrepository.UserInfoRepository;
import com.nikita.simpleProject.repository.secondrepository.SomeRepository;
import com.nikita.simpleProject.security.JwtTokenProvider;
import com.nikita.simpleProject.repository.firstrepository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import static com.nikita.simpleProject.dto.user.UserDto.toDto;

@Service
public class UserService {

    private static final Logger logger = Logger.getGlobal();

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

    @Autowired
    private SomeRepository someRepository;
    //TODO: change ApiMessage to dto as in other project(lc)
    //TODO: create RestExceptionController
    public LoginResponseDto login(User user) throws RegistrationException {
        String username = user.getLogin().trim();
        String password = user.getPassword().trim();
        if (password.isEmpty()) {
            throw new RegistrationException("Введите пароль");
        }
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            Optional<User> dbUser = userRepository.findUserByLogin(username);
            if(dbUser.isPresent()) {
                String token = jwtTokenProvider.createToken(username, dbUser.get().getRoles());
                logger.info(String.valueOf(dbUser));
                return new LoginResponseDto(toDto(dbUser.get()),token);
            }else throw new RegistrationException("Пользователя с таким именем не существует");


        } catch (AuthenticationException e) {
            throw new DefaultException(HttpStatus.UNPROCESSABLE_ENTITY, "Invalid username or password supplied");
        }
        /*{"login": "asd",
                "password":"asd"
        }*/
    }

    public UserDto addUser(User user) throws RegistrationException {
        String username = user.getLogin().toLowerCase();
        Optional<User> dbUser = userRepository.findUserByLogin(username);
        if (dbUser.isPresent()) {
            throw new RegistrationException("Пользователь с таким логином уже зарегистрирован");
        }
        if (user.getPassword().isEmpty()) {
            throw new RegistrationException("Введите пароль");
        }

        if (!user.getPassword().equals(user.getConfirm())) {
            throw new RegistrationException("Пароли не совпадают");
        }
        user.setLogin(username);
        user.setRoles(Collections.singleton(Role.ROLE_USER));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setState(State.ACTIVE);
        user.setUserEnabled(true);
        userRepository.save(user);
        return toDto(user);
        /*{
                "login": "asd",
                "confirm": "asd",
                "password": "asd"
        }*/
    }

    public UserInfo getUserInfo(String userName){
        Optional<UserInfo> userInfoCandidate = userInfoRepository.findByUserName(userName);
        if (userInfoCandidate.isPresent()) {
            userInfoCandidate.get().getUserName().setPassword("");
            return userInfoCandidate.get();
        }else throw new IllegalArgumentException("No information about this user");
    }

    public List<SomeInformation> getAllFromSecondDB(){
        return someRepository.findAll();
    }

}