package org.barmaley.vkr.autentication;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomStudentProvider implements AuthenticationProvider {

    protected static Logger logger = Logger.getLogger("controller");


    private final CustomUserService userService;

    @Autowired
    public CustomStudentProvider(CustomUserService userService) {
        this.userService = userService;
    }

    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();

        CustomUser user = userService.loadStudentByUsername(username);

        if (user == null || !user.getUsername().equalsIgnoreCase(username)) {
            throw new BadCredentialsException("Username not found.");
        }

        if (!password.equals(user.getPassword())) {
            throw new BadCredentialsException("Wrong password.");
        }

        List<GrantedAuthority> authorityList = user.getAuthorities();
        return new UsernamePasswordAuthenticationToken(user, password, authorityList);
    }

    public boolean supports(Class<?> arg0) {
        return true;
    }

}