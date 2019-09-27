package com.nikita.simpleProject.security;

import com.nikita.simpleProject.model.first.User;
import com.nikita.simpleProject.repository.firstrepository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetails implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        final Optional<User> userCandidate = userRepository.findUserByLogin(login);

        User user;
        if (!userCandidate.isPresent()) {
            throw new UsernameNotFoundException("User '" + login + "' not found");
        }  else {
            user = userCandidate.get();
        }

        return org.springframework.security.core.userdetails.User
                .withUsername(login)
                .password(user.getPassword())
                .authorities(user.getRoles())
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
    }
}
