package org.barmaley.vkr.autentication;

import org.apache.log4j.Logger;
import org.barmaley.vkr.domain.EmployeeCopy;
import org.barmaley.vkr.domain.Roles;
import org.barmaley.vkr.domain.StudentCopy;
import org.barmaley.vkr.domain.Users;
import org.barmaley.vkr.service.AuthenticationService;
import org.barmaley.vkr.service.RolesService;
import org.barmaley.vkr.service.StudentCopyService;
import org.barmaley.vkr.service.UsersService;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by gagar on 20.05.2017.
 */

@Transactional
@Repository
public class UserDAOImpl {

    @Resource(name = "authenticationService")
    private AuthenticationService authenticationService;

    @Resource(name = "usersService")
    private UsersService usersService;

    @Resource(name = "rolesService")
    private RolesService rolesService;

    protected static Logger logger = Logger.getLogger("controller");

    public CustomUser loadUserByUsername(final String username){
        CustomUser customUser = new CustomUser();
        try {
            Users user = usersService.getByExtId(username);
            StudentCopy studentCopy = authenticationService.getStudentCopy(username);
            EmployeeCopy employeeCopy = authenticationService.getEmployeeCopy(username);
            if (user != null){
                customUser.setId(user.getId());
                customUser.setUsername(user.getExtId());
                customUser.setFirstName(user.getFirstName());
                customUser.setSecondName(user.getSecondName());
                customUser.setSurname(user.getSurname());
                customUser.setEmail(user.getEmail());
                customUser.setPhoneNumber(user.getPhoneNumber());
                customUser.setAuthorities(authenticationService.getAuthorities(user.getId()));
                if(studentCopy != null){
                    customUser.setPassword(studentCopy.getPassword());
                }else {
                    customUser.setPassword(employeeCopy.getPassword());
                }
                return customUser;
            }

            if(user == null && (studentCopy != null || employeeCopy != null)){
                user = new Users();
                Set<Roles> roles = new HashSet<>();
                if(studentCopy != null){
                    roles.add(rolesService.getRole(1));
                    user.setExtId(studentCopy.getUsername());
                    user.setSurname(studentCopy.getSurname());
                    user.setFirstName(studentCopy.getFirstName());
                    user.setSecondName(studentCopy.getSecondName());
                    user.setEnabled(true);
                    user.setRoles(roles);
                    usersService.addUser(user);
                    user = usersService.getByExtId(username);
                    customUser.setPassword(studentCopy.getPassword());
                }
                if(employeeCopy != null){
                    roles.add(rolesService.getRole(2));
                    user.setExtId(employeeCopy.getUsername());
                    user.setSurname(employeeCopy.getSurname());
                    user.setFirstName(employeeCopy.getFirstName());
                    user.setSecondName(employeeCopy.getSecondName());
                    user.setEnabled(true);
                    user.setRoles(roles);
                    usersService.addUser(user);
                    user = usersService.getByExtId(username);
                    customUser.setPassword(studentCopy.getPassword());
                }
                customUser.setId(user.getId());
                customUser.setUsername(user.getExtId());
                customUser.setFirstName(user.getFirstName());
                customUser.setSecondName(user.getSecondName());
                customUser.setSurname(user.getSurname());
                customUser.setEmail(user.getEmail());
                customUser.setPhoneNumber(user.getPhoneNumber());
                customUser.setAuthorities(authenticationService.getAuthorities(user.getId()));
                return customUser;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    return null;
    }
}
