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
        try {
            Ldap ldap = new Ldap(username, password);
            Abis abis = new Abis();
            String educIdString =abis.searchRecordXML(false, username);
            logger.debug("вышло: "+educIdString);
            int educId = Integer.parseInt(educIdString);

            logger.debug("Прошли try");
            CustomUser user = userService.loadStudentByUsername(username, educId);
            logger.debug("1");
            logger.debug("4");
            List<GrantedAuthority> authorityList = user.getAuthorities();
            logger.debug("5");
            return new UsernamePasswordAuthenticationToken(user, password, authorityList);
        } catch (Exception e) {
           logger.debug("ExpCustomStudentProvider");
            throw new BadCredentialsException("Wrong password.");
        }
        }

    public boolean supports(Class<?> arg0) {
        return true;
    }

}