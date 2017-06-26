package org.barmaley.vkr.autentication;


import org.apache.log4j.Logger;
import org.barmaley.vkr.ldapAuth.Abis;
import org.barmaley.vkr.ldapAuth.Ldap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.naming.NamingException;
import java.util.List;

@Component
public class CustomEmployeeProvider implements AuthenticationProvider {

    protected final Logger logger = Logger.getLogger(CustomProvider.class);

    private final CustomUserService userService;

    @Autowired
    public CustomEmployeeProvider(CustomUserService userService) {
        this.userService = userService;
    }

    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String username = authentication.getName();
        String password = (String) authentication.getCredentials();

        try {
            Ldap ldap = new Ldap(username, password);
            Abis abis = new Abis();
            String fullname =abis.searchRecordXML(true,username);

        CustomUser user = userService.loadEployeeByUsername(username,fullname);

        List<GrantedAuthority> authorityList = user.getAuthorities();
        return new UsernamePasswordAuthenticationToken(user, password, authorityList);
        } catch (NamingException e) {
            throw new BadCredentialsException("Wrong password.");
        }
        catch (Exception e) {
            throw new BadCredentialsException("Wrong password.");
        }
    }
    public boolean supports(Class<?> arg0) {
        return true;
    }
}