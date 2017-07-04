package org.barmaley.vkr.autentication;

import org.apache.log4j.Logger;
import org.barmaley.vkr.domain.Users;
import org.barmaley.vkr.service.StudentCopyService;
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
public class CustomLocalProvider implements AuthenticationProvider {

    protected static Logger logger = Logger.getLogger("controller");


    private final CustomUserService userService;

    @Autowired
    public CustomLocalProvider(CustomUserService userService) {
        this.userService = userService;
    }

    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();
        CustomUser user = new CustomUser();
        try{
            user = userService.loadUserByUsername(username);
            if(user!=null || user.getUsername().equalsIgnoreCase(username)){
                if(password.equals(user.getPassword())){
                    logger.info("User detected!");
                }
            }
        } catch (Exception e){
            try {
                logger.info("User undetected!");
                user = userService.loadStudentByUsername(username, 0);
                if(user!=null || user.getUsername().equalsIgnoreCase(username)){
                    if(password.equals(user.getPassword())){
                        logger.info("Student detected!");
                    }
                }

            }catch (Exception e1){
                try{
                    logger.info("Student undetected!");
                    user = userService.loadEployeeByUsername(username,"ИИИ");
                    if(user!=null || user.getUsername().equalsIgnoreCase(username)){
                        if(password.equals(user.getPassword())){
                            logger.info("Employee detected!");
                        }
                    }
                }catch (Exception e2){throw new BadCredentialsException("User,Student or Employee not found.");}
            }
        }


//        if(user!=null || user.getUsername().equalsIgnoreCase(username)){
//            if(password.equals(user.getPassword())){
//                logger.info("Student detected!");
//            }
//        }else{
//            user = userService.loadStudentByUsername(username, 0);
//
//            if(user!=null || user.getUsername().equalsIgnoreCase(username)){
//                if(password.equals(user.getPassword())){
//                    logger.info("Employee detected!");
//                }
//            }else{
//
//                if(user!=null || user.getUsername().equalsIgnoreCase(username)){
//                    if(password.equals(user.getPassword())){
//                        logger.info("User detected!");
//                    }
//                    user = userService.loadEployeeByUsername(username,"ИИИ");
//                }else{
//                    throw new BadCredentialsException("User,Student or Employee not found.");
//                }
//            }
//        }
        List<GrantedAuthority> authorityList = user.getAuthorities();
        return new UsernamePasswordAuthenticationToken(user, password, authorityList);
    }

    public boolean supports(Class<?> arg0) {
        return true;
    }

}

