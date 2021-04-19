package com.exercises.exercise01.service;

import java.util.ArrayList;

import com.exercises.exercise01.model.JwtRequest;
import com.exercises.exercise01.util.EmailUtil;
import com.exercises.exercise01.util.PasswordUtil;
import com.google.common.base.Joiner;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    public void validateEmailAndPassword(JwtRequest authenticationRequest) throws AuthenticationException {
        String email = authenticationRequest.getEmail();
        if (!EmailUtil.isValidEmail(email)){
            throw new AuthenticationException("INVALID_EMAIL");
        }

        String password = authenticationRequest.getPassword();
        if (!PasswordUtil.isPasswordStrongEnough(password)){
            String messages = Joiner.on(',').join(PasswordUtil.weakPasswordMessages(password));
            throw new AuthenticationException("INVALID_PASSWORD " + messages);
        }
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        if ("user@email.com".equals(email)) {
            return new User("user@email.com", "$2a$10$f6gAjzoitPdFd3L58lgGQ.xJdtc2YM3f9Ko10tuql2zis9G.I39CG",
                    new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
    }
}
