package org.barmaley.vkr.autentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserService implements UserDetailsService {

    private final UserDAOImpl userDao;

    @Autowired
    public CustomUserService(UserDAOImpl userDao) {
        this.userDao = userDao;
    }


    public CustomUser loadUserByUsername(String username) throws UsernameNotFoundException {
        return userDao.loadUserByUsername(username);
    }

    public CustomUser loadStudentByUsername(String username) throws UsernameNotFoundException {
        return userDao.loadStudentByUsername(username);
    }

    public CustomUser loadEployeeByUsername(String username) throws UsernameNotFoundException {
        return userDao.loadEmployeeByUsername(username);
    }

}
