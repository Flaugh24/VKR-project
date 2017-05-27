package org.barmaley.vkr.autentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Created by gagar on 20.05.2017.
 */

@Service
public class CustomUserService implements UserDetailsService {

    @Autowired
    private UserDAOImpl userDao;


    public CustomUser loadUserByUsername(String username) throws UsernameNotFoundException {
        return userDao.loadUserByUsername(username);
    }

}
